package dev.nonentity.stapi.account.service;

import dev.nonentity.stapi.account.contract.CreateApplicationAccount;
import dev.nonentity.stapi.account.contract.ExistingApplicationAccountCredentials;

public interface ApplicationAccountService {
  ExistingApplicationAccountCredentials createApplicationAccount(CreateApplicationAccount request);
}
