package com.payment.app.persistence.streams;

import com.payment.app.lib.dto.TransactionDto;
import com.payment.app.lib.dto.TransactionType;
import com.payment.app.persistence.dto.TransactionStatisticDto;
import java.util.Map;
import org.apache.kafka.streams.kstream.Aggregator;
import org.springframework.stereotype.Component;

@Component
public class TransactionStatisticAggregator implements Aggregator<String, TransactionDto, TransactionStatisticDto>  {

  @Override
  public TransactionStatisticDto apply(String key, TransactionDto transactionDto,
      TransactionStatisticDto aggregate) {

    Map<String, Integer> transactionByIban = aggregate.getTransactionCountByIban();
    transactionByIban.put(transactionDto.getIBAN(),
        transactionByIban.getOrDefault(transactionDto.getIBAN(), 0) + 1);

    Map<TransactionType, Double> transactionSumsByType = aggregate.getTransactionSumsByType();
    transactionSumsByType.put(transactionDto.getTransactionType(),
        transactionSumsByType.get(transactionDto.getTransactionType()) + transactionDto.getSum());

    return aggregate;
  }
}
