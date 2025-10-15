package dev.nonentity.stapi.account.service;

import dev.nonentity.stapi.account.contract.CreateUserAccount;
import dev.nonentity.stapi.account.contract.ExistingUserAccount;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountService {
  ExistingUserAccount createUserAccount(CreateUserAccount request);

  Optional<ExistingUserAccount> findById(UUID userAccountId);

}
