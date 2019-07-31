package com.payment.app.validation.web;

import com.payment.app.lib.dto.TransactionDto;
import com.payment.app.validation.service.IntegrationService;
import com.payment.app.validation.service.ValidationService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

  private final ValidationService validationService;
  private final IntegrationService integrationService;

  public TransactionController(
      ValidationService validationService,
      IntegrationService integrationService) {
    this.validationService = validationService;
    this.integrationService = integrationService;
  }

  @PostMapping(path = "/transaction",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> processTransaction(@RequestBody @Valid TransactionDto transactionDto) {

    validationService.validateTransaction(transactionDto);
    integrationService.publishTransaction(transactionDto);

    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .build();
  }
}
