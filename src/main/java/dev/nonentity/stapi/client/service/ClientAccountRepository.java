package dev.nonentity.stapi.client.service;

import dev.nonentity.stapi.client.domain.ClientAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientAccountRepository extends JpaRepository<ClientAccount, UUID> {

  Optional<ClientAccount> findByAlias(String alias);

}
