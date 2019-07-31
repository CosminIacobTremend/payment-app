package com.payment.app.validation.persistence;

import com.payment.app.lib.MicroService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@MicroService
@SpringBootApplication(scanBasePackages = {"com.payment.app"})
public class PaymentPersistenceApp {

  public static void main(String[] args) {
    SpringApplication.run(PaymentPersistenceApp.class, args);
  }

}
