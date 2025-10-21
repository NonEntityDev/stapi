package dev.nonentity.stapi.client.service;

import dev.nonentity.stapi.client.contract.CreateClientAccount;
import dev.nonentity.stapi.client.contract.ExistingClientAccount;
import dev.nonentity.stapi.client.contract.ExistingClientAccountCredentials;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ClientAccountService {
  ExistingClientAccountCredentials create(CreateClientAccount request);

  Set<ExistingClientAccount> loadAll();

  Optional<ExistingClientAccount> remove(UUID clientId);
}
