package com.payment.app.validation.test;

import static io.restassured.RestAssured.with;

import com.payment.app.lib.dto.TransactionDto;
import com.payment.app.lib.dto.TransactionType;
import com.payment.app.validation.PaymentValidationApp;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaymentValidationApp.class,
    webEnvironment = WebEnvironment.RANDOM_PORT)
public class ValidationIntegrationTest {

  @LocalServerPort
  private int serverPort;

  private final String TRANSACTION_ENDPOINT = "/transaction";

  @Before
  public void setup() {
    RestAssured.port = serverPort;
  }

  @Test
  public void contextLoads(){
  }

  @Test
  public void testValidTransaction() {
    TransactionDto transactionDto = TransactionDto.builder()
        .CNP("test_cnp_2")
        .IBAN("test_iban_4")
        .name("test_fn_4 test_ln_4")
        .sum(10)
        .transactionType(TransactionType.IBAN_TO_WALLET)
        .build();
    with().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .body(transactionDto)
        .when()
        .post(TRANSACTION_ENDPOINT)
        .then()
        .statusCode(HttpStatus.SC_ACCEPTED);
  }

  @Test
  public void testInvalidCNP() {
    TransactionDto transactionDto = TransactionDto.builder()
        .CNP("invalid_cnp")
        .IBAN("test_iban_4")
        .name("test_fn_4 test_ln_4")
        .sum(10)
        .transactionType(TransactionType.IBAN_TO_WALLET)
        .build();

    with().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .body(transactionDto)
        .when()
        .post(TRANSACTION_ENDPOINT)
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  public void testInvalidIban() {
    TransactionDto transactionDto = TransactionDto.builder()
        .CNP("test_cnp_1")
        .IBAN("invalid_iban")
        .name("test_fn_4 test_ln_4")
        .sum(10)
        .transactionType(TransactionType.IBAN_TO_WALLET)
        .build();

    with().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .body(transactionDto)
        .when()
        .post(TRANSACTION_ENDPOINT)
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  public void testNameNotCorrespondingToIban() {
    TransactionDto transactionDto = TransactionDto.builder()
        .CNP("test_cnp_1")
        .IBAN("test_iban_4")
        .name("invalid_name")
        .sum(10)
        .transactionType(TransactionType.IBAN_TO_WALLET)
        .build();

    with().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .body(transactionDto)
        .when()
        .post(TRANSACTION_ENDPOINT)
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

  @Test
  public void testSumBiggerThanZero() {
    TransactionDto transactionDto = TransactionDto.builder()
        .CNP("test_cnp_2")
        .IBAN("test_iban_4")
        .name("test_fn_4 test_ln_4")
        .sum(-10)
        .transactionType(TransactionType.IBAN_TO_WALLET)
        .build();
    with().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .body(transactionDto)
        .when()
        .post(TRANSACTION_ENDPOINT)
        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }


}
