package dev.nonentity.stapi.client.service;

import dev.nonentity.stapi.client.contract.ExistingClientAccountCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class RegisteredClientAccountRepository implements RegisteredClientRepository {

  private final ClientAccountService clientAccountService;

  public RegisteredClientAccountRepository(ClientAccountService clientAccountService) {
    this.clientAccountService = clientAccountService;
  }

  @Override
  public void save(RegisteredClient registeredClient) {
    throw new UnsupportedOperationException("Use POST /api/v1/clients instead.");
  }

  @Override
  public RegisteredClient findById(String id) {
    return this.findByClientId(id);
  }

  @Override
  public RegisteredClient findByClientId(String clientId) {
    log.info("Authenticating client account.");
    ExistingClientAccountCredentials clientAccount = this.clientAccountService.findByIdWithCredentials(
            UUID.fromString(clientId)
    ).orElseThrow(() -> new IllegalArgumentException("Client id not found."));

    RegisteredClient.Builder registeredClient = RegisteredClient
            .withId(clientAccount.getClientId().toString())
            .clientId(clientAccount.getClientId().toString())
            .clientSecret(clientAccount.getClientSecret())
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST);

    clientAccount.getScopes().forEach(registeredClient::scope);

    return registeredClient.build();
  }
}
