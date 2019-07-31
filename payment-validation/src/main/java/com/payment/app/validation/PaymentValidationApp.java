package com.payment.app.validation;

import com.payment.app.lib.MicroService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.payment.app"})
@MicroService
public class PaymentValidationApp {

  public static void main(String[] args) {
    SpringApplication.run(PaymentValidationApp.class, args);
  }
}
