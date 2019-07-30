package com.payment.app.validation;

import com.payment.app.lib.MicroService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MicroService
public class PaymentValidationApp {

  public static void main(String[] args) {
    SpringApplication.run(PaymentValidationApp.class, args);
  }
}
