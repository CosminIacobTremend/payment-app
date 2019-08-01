package com.payment.app.persistence.test;

import static io.restassured.RestAssured.get;

import com.payment.app.persistence.PaymentPersistenceApp;
import io.restassured.RestAssured;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaymentPersistenceApp.class,
    webEnvironment = WebEnvironment.RANDOM_PORT)
public class PaymentIntegrationTest {

  @LocalServerPort
  private int serverPort;

  private final String TRANSACTION_STATISTIC_ENDPOINT = "/transaction_statistics";

  @Before
  public void setup() {
    RestAssured.port = serverPort;
  }

  @Test
  public void contextLoads(){
  }

  @Test
  public void getTransactionStatistics() {
    get(TRANSACTION_STATISTIC_ENDPOINT)
        .then()
        .statusCode(HttpStatus.OK_200);
  }

}
