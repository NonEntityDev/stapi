package dev.nonentity.stapi.account.service;

import dev.nonentity.stapi.account.contract.CreateApplicationAccount;
import dev.nonentity.stapi.account.contract.ExistingApplicationAccountCredentials;
import dev.nonentity.stapi.account.domain.ApplicationAccount;
import dev.nonentity.stapi.account.repository.ApplicationAccountRepository;
import dev.nonentity.stapi.common.RequestValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

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

}
