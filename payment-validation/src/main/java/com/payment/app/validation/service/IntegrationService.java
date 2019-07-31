package com.payment.app.validation.service;

import com.payment.app.lib.config.KafkaConfig;
import com.payment.app.lib.dto.TransactionDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class IntegrationService {

  private final KafkaConfig kafkaConfig;
  private final KafkaTemplate<String, TransactionDto> kafkaTransactionTemplate;

  public IntegrationService(KafkaConfig kafkaConfig,
      KafkaTemplate<String, TransactionDto> kafkaTransactionTemplate) {
    this.kafkaConfig = kafkaConfig;
    this.kafkaTransactionTemplate = kafkaTransactionTemplate;
  }

  public void publishTransaction(TransactionDto transactionDto) {
    kafkaTransactionTemplate.send(kafkaConfig.getTransactionTopic(), transactionDto.getCNP(),
        transactionDto);
  }
}
