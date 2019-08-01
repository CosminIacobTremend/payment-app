package com.payment.app.persistence.dto;

import com.payment.app.lib.dto.TransactionType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.apache.kafka.streams.kstream.Initializer;

@Data
public class TransactionStatisticDto implements Initializer<TransactionStatisticDto> {

  private String cnp;
  private Map<TransactionType, Double> transactionSumsByType;
  private Map<String, Integer> transactionCountByIban;

  public TransactionStatisticDto() {
    transactionSumsByType = new HashMap<>();
    transactionCountByIban = new HashMap<>();
    Arrays.stream(TransactionType.values())
        .forEach(t -> transactionSumsByType.put(t, 0.d));
  }


  @Override
  public TransactionStatisticDto apply() {
    return new TransactionStatisticDto();
  }
}
