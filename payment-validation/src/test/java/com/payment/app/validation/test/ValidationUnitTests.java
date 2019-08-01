package com.payment.app.validation.test;

import com.payment.app.lib.dto.TransactionDto;
import com.payment.app.lib.dto.TransactionType;
import com.payment.app.lib.exceptions.InvalidTransactionException;
import com.payment.app.lib.model.Account;
import com.payment.app.lib.model.Client;
import com.payment.app.lib.repository.AccountRepository;
import com.payment.app.lib.repository.ClientRepository;
import com.payment.app.validation.service.ValidationService;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class ValidationUnitTests {

  @MockBean
  private AccountRepository accountRepository;
  @MockBean
  private ClientRepository clientRepository;

  private ValidationService validationService;

  @Before
  public void setUp() {
    Client client = new Client();
    client.setCnp("123");
    client.setFirstName("fn");
    client.setLastName("ln");

    Account account = new Account();
    account.setClient(client);
    account.setIban("test_iban");

    Mockito.when(clientRepository.getClientByCnp("123"))
        .thenReturn(Optional.of(client));
    Mockito.when(clientRepository.getClientByCnp("wrong_cnp"))
        .thenReturn(Optional.empty());

    Mockito.when(accountRepository.getAccountWithClientByIban("test_iban"))
        .thenReturn(Optional.of(account));

    Mockito.when(accountRepository.getAccountWithClientByIban("wrong_iban"))
        .thenReturn(Optional.empty());

    validationService = new ValidationService(accountRepository, clientRepository);

  }

  @Test
  public void testTransactionValidation(){
    TransactionDto transactionDto = TransactionDto.builder()
        .CNP("123")
        .IBAN("test_iban")
        .name("fn ln")
        .sum(10)
        .transactionType(TransactionType.IBAN_TO_WALLET)
        .build();

    validationService.validateTransaction(transactionDto);
  }

  @Test(expected = InvalidTransactionException.class)
  public void testInvalidCNp() {
    TransactionDto transactionDto = TransactionDto.builder()
        .CNP("wrong_cnp")
        .IBAN("test_iban")
        .name("fn ln")
        .sum(10)
        .transactionType(TransactionType.IBAN_TO_WALLET)
        .build();

    validationService.validateTransaction(transactionDto);
  }

  @Test(expected = InvalidTransactionException.class)
  public void testInvalidIban() {
    TransactionDto transactionDto = TransactionDto.builder()
        .CNP("123")
        .IBAN("wrong_iban")
        .name("fn ln")
        .sum(10)
        .transactionType(TransactionType.IBAN_TO_WALLET)
        .build();

    validationService.validateTransaction(transactionDto);
  }
}
