package com.payment.app.validation.persistence.streams;

import com.payment.app.lib.config.KafkaConfig;
import com.payment.app.lib.dto.TransactionDto;
import com.payment.app.validation.persistence.config.KafkaStoreConstants;
import com.payment.app.validation.persistence.dto.TransactionStatisticDto;
import com.payment.app.validation.persistence.service.TransactionService;
import javax.annotation.PostConstruct;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Serialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class TransactionProcessingStreams {

  private final KStream<String, TransactionDto> inboundTransactionStream;
  private final TransactionService transactionService;
  private final TransactionStatisticAggregator transactionStatisticAggregator;

  public TransactionProcessingStreams(
      @Qualifier("inboundTransactionStream") KStream<String, TransactionDto> inboundTransactionStream,
      TransactionService transactionService,
      TransactionStatisticAggregator transactionStatisticAggregator) {
    this.inboundTransactionStream = inboundTransactionStream;
    this.transactionService = transactionService;
    this.transactionStatisticAggregator = transactionStatisticAggregator;
  }

  @PostConstruct
  public void defineTransactionPersistence() {
    inboundTransactionStream.foreach((k, v) -> transactionService.saveTransaction(v));
  }

  @Bean
  @Qualifier("transactionStatisticTable")
  public GlobalKTable<String, TransactionStatisticDto> transactionStatisticTable(
      @Qualifier("inboundTransactionStream") KStream<String, TransactionDto> inboundTransactionStream,
      @Qualifier("streamBuilder") StreamsBuilderFactoryBean streamBuilderFactoryBean,
      KafkaConfig kafkaConfig) throws Exception {

    KTable<String, TransactionStatisticDto> transactionStatisticDtoKTable = inboundTransactionStream
        .groupByKey(Serialized.with(Serdes.String(),  new JsonSerde<>(TransactionDto.class)))
        .aggregate(new TransactionStatisticDto(),
            transactionStatisticAggregator,
            Materialized.<String, TransactionStatisticDto, KeyValueStore<Bytes, byte[]>>as(
                KafkaStoreConstants.G_TRANSACTION_STATISTIC_TABLE_STORE)
                .withKeySerde(Serdes.String())
                .withValueSerde(new JsonSerde<>(TransactionStatisticDto.class))
                .withCachingDisabled());

    transactionStatisticDtoKTable.toStream()
        .to(kafkaConfig.getStatisticTopic(), Produced.with(Serdes.String(), new JsonSerde<>(TransactionStatisticDto.class)));

    return streamBuilderFactoryBean.getObject().globalTable(kafkaConfig.getStatisticTopic(),
        Consumed.with(Serdes.String(), new JsonSerde<>(TransactionStatisticDto.class)),
        Materialized.<String, TransactionStatisticDto, KeyValueStore<Bytes, byte[]>>as(
            KafkaStoreConstants.G_TRANSACTION_STATISTIC_TABLE_STORE)
            .withKeySerde(Serdes.String())
            .withValueSerde(new JsonSerde<>(TransactionStatisticDto.class))
            .withCachingDisabled());

  }
}
