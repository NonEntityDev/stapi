package dev.nonentity.stapi.client.service;

import dev.nonentity.stapi.client.contract.CreateClientAccount;
import dev.nonentity.stapi.client.contract.ExistingClientAccountCredentials;
import dev.nonentity.stapi.client.domain.ClientAccount;
import dev.nonentity.stapi.common.RequestValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

}
