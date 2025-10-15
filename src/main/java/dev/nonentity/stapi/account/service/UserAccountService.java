package dev.nonentity.stapi.account.service;

import dev.nonentity.stapi.account.contract.CreateUserAccount;
import dev.nonentity.stapi.account.contract.ExistingUserAccount;

public interface UserAccountService {
  ExistingUserAccount createUserAccount(CreateUserAccount request);
}
