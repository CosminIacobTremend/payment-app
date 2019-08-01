package com.payment.app.validation.persistence.service;

import com.payment.app.validation.persistence.dto.TransactionStatisticDto;
import java.util.ArrayList;
import java.util.List;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class TransactionStatisticService {
  private final KTable<String, TransactionStatisticDto> transactionStatisticTable;
  private final StreamsBuilderFactoryBean streamsBuilderFactoryBean;

  public TransactionStatisticService(
      KTable<String, TransactionStatisticDto> transactionStatisticTable,
      @Qualifier("streamBuilder") StreamsBuilderFactoryBean streamsBuilderFactoryBean) {
    this.transactionStatisticTable = transactionStatisticTable;
    this.streamsBuilderFactoryBean = streamsBuilderFactoryBean;
  }

  public List<TransactionStatisticDto> getTransactionStatistics() {

    ReadOnlyKeyValueStore view = streamsBuilderFactoryBean
        .getKafkaStreams()
        .store(transactionStatisticTable.queryableStoreName(), QueryableStoreTypes.keyValueStore());

    KeyValueIterator<String, TransactionStatisticDto> iterator = view.all();

    List<TransactionStatisticDto> transactionStatisticDtos = new ArrayList<>();

    while (iterator.hasNext()) {
      KeyValue<String, TransactionStatisticDto> next = iterator.next();
      TransactionStatisticDto statisticDto = next.value;
      statisticDto.setCnp(next.key);
      transactionStatisticDtos.add(statisticDto);
    }

    return  transactionStatisticDtos;
  }
}
