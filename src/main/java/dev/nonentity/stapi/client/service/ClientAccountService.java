package dev.nonentity.stapi.client.service;

import dev.nonentity.stapi.client.contract.CreateClientAccount;
import dev.nonentity.stapi.client.contract.ExistingClientAccount;
import dev.nonentity.stapi.client.contract.ExistingClientAccountCredentials;
import dev.nonentity.stapi.client.contract.UpdateClientAccount;
import dev.nonentity.stapi.client.contract.UpdateClientAccountCredentials;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ClientAccountService {
  ExistingClientAccountCredentials create(CreateClientAccount request);

  Set<ExistingClientAccount> loadAll();

  Optional<ExistingClientAccount> remove(UUID clientId);

  Optional<ExistingClientAccount> update(UUID clientId, UpdateClientAccount request);

  Optional<ExistingClientAccountCredentials> updateCredentials(UUID clientId, UpdateClientAccountCredentials request);
}
