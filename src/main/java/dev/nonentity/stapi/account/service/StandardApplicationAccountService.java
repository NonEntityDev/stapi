package dev.nonentity.stapi.account.service;

import dev.nonentity.stapi.account.contract.CreateApplicationAccount;
import dev.nonentity.stapi.account.contract.ExistingApplicationAccount;
import dev.nonentity.stapi.account.contract.ExistingApplicationAccountCredentials;
import dev.nonentity.stapi.account.domain.ApplicationAccount;
import dev.nonentity.stapi.account.repository.ApplicationAccountRepository;
import dev.nonentity.stapi.common.RequestValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StandardApplicationAccountService implements ApplicationAccountService {

  private final ApplicationAccountRepository repository;
  private final SecureRandom secureRandom;

  public StandardApplicationAccountService(ApplicationAccountRepository repository) {
    this.secureRandom = new SecureRandom();
    this.repository = repository;
  }

  @Override
  public ExistingApplicationAccountCredentials createApplicationAccount(CreateApplicationAccount request) {
    log.info("Creating a new application account.");
    log.debug("New application account details: {}", request);

    Optional<ApplicationAccount> duplicatedApplicationAccount = this.repository.findByAlias(request.getAlias());
    if (duplicatedApplicationAccount.isPresent()) {
      throw new RequestValidationException(request.getClass().getSimpleName(), "alias", "already exists");
    }

    ApplicationAccount newApplicationAccount = request.toEntity();
    newApplicationAccount.setSecret(this.generateApplicationSecret());
    ExistingApplicationAccountCredentials applicationAccount = ExistingApplicationAccountCredentials.fromEntity(
            this.repository.save(newApplicationAccount)
    );

    log.info("New application account successfully created.");
    return applicationAccount;
  }

  private String generateApplicationSecret() {
    byte[] rawSecret = new byte[48];
    this.secureRandom.nextBytes(rawSecret);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(rawSecret);
  }

  @Override
  public Optional<ExistingApplicationAccount> findById(UUID applicationId) {
    log.info("Retrieving application account by id.");
    log.debug("Requested application account id: {}", applicationId);
    return this.repository.findById(applicationId).map(ExistingApplicationAccount::fromEntity);
  }

  @Override
  public Set<ExistingApplicationAccount> findAll() {
    log.info("Retrieving all existing application accounts.");
    return this.repository.findAll()
            .stream()
            .map(ExistingApplicationAccount::fromEntity)
            .sorted(Comparator.comparing(ExistingApplicationAccount::getName))
            .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  @Override
  public Optional<ExistingApplicationAccount> removeApplicationAccount(UUID applicationAccountId) {
    log.info("Fetching and removing existing application account.");
    log.debug("Id of application account to be removed: {}", applicationAccountId);
    return this.repository.findById(applicationAccountId)
            .map((ApplicationAccount applicationAccount) -> {
              this.repository.delete(applicationAccount);
              return ExistingApplicationAccount.fromEntity(applicationAccount);
            });
  }

}
