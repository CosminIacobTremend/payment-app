package com.payment.app.lib.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "integration.kafka")
@Getter
@Setter
public class KafkaConfig {
  private String transactionTopic;
  private String statisticTopic;

}
