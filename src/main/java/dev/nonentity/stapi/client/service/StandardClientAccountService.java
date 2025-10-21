package dev.nonentity.stapi.client.service;

import dev.nonentity.stapi.client.contract.CreateClientAccount;
import dev.nonentity.stapi.client.contract.ExistingClientAccount;
import dev.nonentity.stapi.client.contract.ExistingClientAccountCredentials;
import dev.nonentity.stapi.client.contract.UpdateClientAccount;
import dev.nonentity.stapi.client.domain.ClientAccount;
import dev.nonentity.stapi.common.RequestValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StandardClientAccountService implements ClientAccountService {

  private final ClientAccountRepository clientAccountRepository;
  private final PasswordEncoder passwordEncoder;

  public StandardClientAccountService(ClientAccountRepository clientAccountRepository, PasswordEncoder passwordEncoder) {
    this.clientAccountRepository = clientAccountRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public ExistingClientAccountCredentials create(CreateClientAccount request) {
    log.info("Creating a new client account.");

    Optional<ClientAccount> existingClientAccount = this.clientAccountRepository.findByAlias(request.getAlias());
    if (existingClientAccount.isPresent()) {
      throw new RequestValidationException(request.getClass().getSimpleName(), "alias", "already exists");
    }

    String secretHash = this.passwordEncoder.encode(request.getClientSecret());
    ClientAccount newClientAccount = request.toEntity();
    newClientAccount.setSecretHash(secretHash);

    return new ExistingClientAccountCredentials(
            this.clientAccountRepository.save(newClientAccount)
    );

  }

  @Override
  public Set<ExistingClientAccount> loadAll() {
    log.info("Retrieving all client accounts available.");
    return this.clientAccountRepository.findAll()
            .stream()
            .map(ExistingClientAccount::new)
            .sorted(Comparator.comparing(ExistingClientAccount::getAlias))
            .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  @Override
  public Optional<ExistingClientAccount> remove(UUID clientId) {
    log.info("Removing existing client account.");
    return this.clientAccountRepository.findById(clientId)
            .map((ClientAccount entity) -> {
              this.clientAccountRepository.delete(entity);
              return new ExistingClientAccount(entity);
            });
  }

  @Override
  public Optional<ExistingClientAccount> update(UUID clientId, UpdateClientAccount request) {
    log.info("Updating existing client account.");

    Optional<ClientAccount> duplicatedClientAccount = this.clientAccountRepository.findByAliasAndClientIdNot(
            request.getAlias(), clientId
    );
    if (duplicatedClientAccount.isPresent()) {
      throw new RequestValidationException(request.getClass().getSimpleName(), "alias", "already exists");
    }

    return this.clientAccountRepository.findById(clientId)
            .map((ClientAccount entity) -> {
              request.mergeWith(entity);
              return new ExistingClientAccount(
                      this.clientAccountRepository.save(entity)
              );
            });
  }

}
