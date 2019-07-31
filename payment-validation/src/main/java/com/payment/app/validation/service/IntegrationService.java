package com.payment.app.validation.service;

import com.payment.app.lib.config.KafkaConfig;
import com.payment.app.lib.dto.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
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
    ListenableFuture<SendResult<String, TransactionDto>> future = kafkaTransactionTemplate.send(kafkaConfig.getTransactionTopic(), transactionDto.getCNP(),
        transactionDto);
    future.addCallback(new ListenableFutureCallback<SendResult<String, TransactionDto>>() {

      @Override
      public void onSuccess(SendResult<String, TransactionDto> result) {
        log.info("Sent message=[" + result.getProducerRecord().toString() +
            "] with offset=[" + result.getRecordMetadata().offset() + "]");
      }
      @Override
      public void onFailure(Throwable ex) {
        System.out.println("Unable to send message due to : " + ex.getMessage());
      }
    });
  }
}
