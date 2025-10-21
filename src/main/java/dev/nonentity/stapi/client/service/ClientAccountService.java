package dev.nonentity.stapi.client.service;

import dev.nonentity.stapi.client.contract.CreateClientAccount;
import dev.nonentity.stapi.client.contract.ExistingClientAccountCredentials;

public interface ClientAccountService {
  ExistingClientAccountCredentials create(CreateClientAccount request);
}
