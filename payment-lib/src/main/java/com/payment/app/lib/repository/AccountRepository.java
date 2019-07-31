package com.payment.app.lib.repository;

import com.payment.app.lib.model.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository  extends JpaRepository<Account, Long> {

  Optional<Account> getAccountByIban(String iban);

  @EntityGraph(value = "Account.client")
  Optional<Account> getAccountWithClientByIban(String iban);

}
