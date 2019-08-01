package com.payment.app.validation.persistence.web;

import com.payment.app.validation.persistence.service.TransactionStatisticService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionStatisticController {

  private final TransactionStatisticService transactionStatisticService;

  public TransactionStatisticController(
      TransactionStatisticService transactionStatisticService) {
    this.transactionStatisticService = transactionStatisticService;
  }

  @GetMapping(value = "transaction_statistics", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> getTransactionStatistics() {
    return ResponseEntity.ok(transactionStatisticService.getTransactionStatistics());
  }

}
