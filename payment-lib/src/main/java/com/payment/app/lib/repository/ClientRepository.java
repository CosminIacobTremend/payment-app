package com.payment.app.lib.repository;

import com.payment.app.lib.model.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

  Optional<Client> getClientByCnp(String cnp);

}
