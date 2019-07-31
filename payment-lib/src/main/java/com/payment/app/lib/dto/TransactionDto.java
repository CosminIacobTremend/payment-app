package com.payment.app.lib.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {

  @NotEmpty
  @JsonProperty("IBAN")
  private String IBAN;
  @NotEmpty
  @JsonProperty("CNP")
  private String CNP;
  @NotEmpty
  private String name;
  private String description;
  @Min(0)
  private double sum;
  @NotNull
  private TransactionType transactionType;

}
