package com.payment.app.persistence.config;

import com.payment.app.lib.config.KafkaConfig;
import com.payment.app.lib.dto.TransactionDto;
import java.util.Properties;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class KafkaConsumerConfig {

  @Bean
  public StreamsConfig transactionStreamsConfig(ApplicationContext applicationContext, KafkaProperties kafkaProperties) {
    Properties props = new Properties();
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationContext.getId());

    return new StreamsConfig(props);
  }

  @Bean
  @Qualifier("streamBuilder")
  public StreamsBuilderFactoryBean streamBuilderFactoryBean(StreamsConfig streamsConfig) {
    return new StreamsBuilderFactoryBean(streamsConfig);
  }

  @Bean
  @Qualifier("inboundTransactionStream")
  public KStream<String, TransactionDto> inboundTransactionStream(
      @Qualifier("streamBuilder")StreamsBuilderFactoryBean streamBuilderFactoryBean,
      KafkaConfig kafkaConfig) throws Exception {

    return streamBuilderFactoryBean.getObject()
        .stream(kafkaConfig.getTransactionTopic(), Consumed.with(Serdes.String(), new JsonSerde<>(TransactionDto.class)));
  }



}
