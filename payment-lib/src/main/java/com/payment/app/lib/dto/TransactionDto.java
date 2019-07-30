package com.payment.app.lib.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {

  private String IBAN;
  private String CNP;
  private String name;
  private String description;
  private double sum;
  private TransactionType transactionType;

}
