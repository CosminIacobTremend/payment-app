package com.payment.app.persistence;

import com.payment.app.lib.MicroService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MicroService
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.payment.app"})
public class PaymentPersistenceApp {

  public static void main(String[] args) {
    SpringApplication.run(PaymentPersistenceApp.class, args);
  }

}
