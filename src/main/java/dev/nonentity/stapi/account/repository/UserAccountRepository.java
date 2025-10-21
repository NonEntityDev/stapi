package dev.nonentity.stapi.account.repository;

import dev.nonentity.stapi.account.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {

  Optional<UserAccount> findByAlias(String alias);

}
