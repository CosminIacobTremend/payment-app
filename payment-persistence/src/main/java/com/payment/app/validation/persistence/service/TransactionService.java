package com.payment.app.validation.persistence.service;

import com.payment.app.lib.dto.TransactionDto;
import com.payment.app.lib.dto.TransactionType;
import com.payment.app.lib.model.Transaction;
import com.payment.app.lib.repository.AccountRepository;
import com.payment.app.lib.repository.ClientRepository;
import com.payment.app.lib.repository.TransactionRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final AccountRepository accountRepository;
  private final ClientRepository clientRepository;

  public TransactionService(
      TransactionRepository transactionRepository,
      AccountRepository accountRepository,
      ClientRepository clientRepository) {

    this.transactionRepository = transactionRepository;
    this.accountRepository = accountRepository;
    this.clientRepository = clientRepository;
  }

  @Transactional
  public Transaction saveTransaction(TransactionDto transactionDto) {
    Transaction t = fromDto(transactionDto);
    transactionRepository.save(t);
    return t;
  }

  private Transaction fromDto(TransactionDto transactionDto) {

    return Transaction.builder()
        .account(accountRepository.getAccountByIban(transactionDto.getIBAN()).get())
        .client(clientRepository.getClientByCnp(transactionDto.getCNP()).get())
        .name(transactionDto.getName())
        .description(transactionDto.getDescription())
        .transactionType(transactionDto.getTransactionType().toString())
        .sum(transactionDto.getSum())
        .build();
  }

}
