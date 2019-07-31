package com.payment.app.validation.persistence.streams;

import com.payment.app.lib.dto.TransactionDto;
import com.payment.app.validation.persistence.service.TransactionService;
import javax.annotation.PostConstruct;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionProcessingStreams {

  private final KStream<String, TransactionDto> inboundTransactionStream;
  private final TransactionService transactionService;

  public TransactionProcessingStreams(
      @Qualifier("inboundTransactionStream") KStream<String, TransactionDto> inboundTransactionStream,
      TransactionService transactionService) {
    this.inboundTransactionStream = inboundTransactionStream;
    this.transactionService = transactionService;
  }

  @PostConstruct
  public void defineTransactionPersistence() {
    inboundTransactionStream.foreach((k, v) -> transactionService.saveTransaction(v));
  }




}
