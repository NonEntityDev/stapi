package dev.nonentity.stapi.account.service;

import dev.nonentity.stapi.account.contract.CreateApplicationAccount;
import dev.nonentity.stapi.account.contract.ExistingApplicationAccount;
import dev.nonentity.stapi.account.contract.ExistingApplicationAccountCredentials;

import java.util.Optional;
import java.util.UUID;

public interface ApplicationAccountService {
  ExistingApplicationAccountCredentials createApplicationAccount(CreateApplicationAccount request);

  Optional<ExistingApplicationAccount> findById(UUID applicationId);
}
