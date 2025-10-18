package dev.nonentity.stapi.account.repository;

import dev.nonentity.stapi.account.domain.ApplicationAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ApplicationAccountRepository extends JpaRepository<ApplicationAccount, UUID> {

  Optional<ApplicationAccount> findByAlias(String alias);

}
