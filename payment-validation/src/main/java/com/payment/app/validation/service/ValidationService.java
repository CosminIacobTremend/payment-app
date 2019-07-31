package com.payment.app.validation.service;

import com.payment.app.lib.dto.TransactionDto;
import com.payment.app.lib.exceptions.InvalidTransactionException;
import com.payment.app.lib.model.Account;
import com.payment.app.lib.model.Client;
import com.payment.app.lib.repository.AccountRepository;
import com.payment.app.lib.repository.ClientRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

  private final AccountRepository accountRepository;
  private final ClientRepository clientRepository;

  public ValidationService(AccountRepository accountRepository,
      ClientRepository clientRepository) {
    this.accountRepository = accountRepository;
    this.clientRepository = clientRepository;
  }

  @Transactional
  public void validateTransaction(TransactionDto transactionDto) {
    clientRepository.getClientByCnp(transactionDto.getCNP())
        .orElseThrow(()->
            new InvalidTransactionException("Could not find client with CNP "+ transactionDto.getCNP()));

    Account account = accountRepository.getAccountWithClientByIban(transactionDto.getIBAN())
        .orElseThrow(() ->
            new InvalidTransactionException("IBAN  "+ transactionDto.getIBAN() + " is not associated with any account"));

    Client receiver = account.getClient();

    String[] nameParts = transactionDto.getName().trim().split("\\s+");

    if (nameParts.length != 2 || !nameParts[0].equals(receiver.getFirstName())
        || !nameParts[1].equals(receiver.getLastName())) {
      throw new InvalidTransactionException(
          transactionDto.getName() + " name doesn't correspond to IBAN " + transactionDto.getIBAN());
    }
  }

}
