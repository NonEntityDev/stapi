package dev.nonentity.stapi.account.service;

import dev.nonentity.stapi.account.contract.CreateUserAccount;
import dev.nonentity.stapi.account.contract.ExistingUserAccount;
import dev.nonentity.stapi.account.domain.UserAccount;
import dev.nonentity.stapi.account.repository.UserAccountRepository;
import dev.nonentity.stapi.common.RequestValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
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
public class StandardUserAccountService implements UserAccountService {

  private final UserAccountRepository repository;
  private final PasswordEncoder passwordEncoder;

  public StandardUserAccountService(UserAccountRepository repository,
                                    @Qualifier("userAccountPasswordEncoder") PasswordEncoder passwordEncoder) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public ExistingUserAccount createUserAccount(CreateUserAccount request) {
    log.info("Creating a new user account.");
    log.debug("Received request: {}", request);

    Optional<UserAccount> duplicatedUserAccount = this.repository.findByAlias(request.getLogin());
    if (duplicatedUserAccount.isPresent()) {
      throw new RequestValidationException(request.getClass().getSimpleName(), "login", "already exists");
    }

    UserAccount newUserAccount = request.toEntity();
    newUserAccount.setSecret(this.passwordEncoder.encode(request.getPassword()));
    ExistingUserAccount persistedUserAccount = ExistingUserAccount.fromEntity(repository.save(newUserAccount));

    log.info("New user account successfully created.");
    return persistedUserAccount;
  }

  @Override
  public Optional<ExistingUserAccount> findById(UUID userAccountId) {
    log.info("Retrieving user account by id.");
    log.debug("Received user account id: {}", userAccountId);
    return this.repository.findById(userAccountId)
            .map(ExistingUserAccount::fromEntity);
  }

  @Override
  public Set<ExistingUserAccount> findAll() {
    log.info("Retrieving all existing user accounts");
    return this.repository.findAll()
            .stream()
            .map(ExistingUserAccount::fromEntity)
            .sorted(Comparator.comparing(ExistingUserAccount::getFullName))
            .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  @Override
  public Optional<ExistingUserAccount> removeUserAccount(UUID userAccountId) {
    log.info("Fetching and removing existing user account.");
    log.debug("Id of user account to be removed: {}", userAccountId);
    return this.repository.findById(userAccountId)
            .map((UserAccount userAccount) -> {
              this.repository.delete(userAccount);
              return ExistingUserAccount.fromEntity(userAccount);
            });
  }
}
