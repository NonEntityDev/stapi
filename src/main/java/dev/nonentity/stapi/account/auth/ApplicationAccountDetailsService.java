package dev.nonentity.stapi.account.auth;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;


@Service
public class ApplicationAccountDetailsService implements RegisteredClientRepository {
  @Override
  public void save(RegisteredClient registeredClient) {

  }

  @Override
  public RegisteredClient findById(String id) {
    return null;
  }

  @Override
  public RegisteredClient findByClientId(String clientId) {
    return null;
  }
  /*
  private final ApplicationAccountRepository applicationAccountRepository;

  public ApplicationAccountDetailsService(ApplicationAccountRepository applicationAccountRepository) {
    this.applicationAccountRepository = applicationAccountRepository;
  }

  @Override
  public void save(RegisteredClient registeredClient) {

  }

  @Override
  public RegisteredClient findById(String id) {
    return null;
  }

  @Override
  public RegisteredClient findByClientId(String clientId) {
    return this.applicationAccountRepository.findById(UUID.fromString(clientId))
            .map(this::toRegisteredClient)
            .orElse(null);
  }

  private RegisteredClient toRegisteredClient(ApplicationAccount applicationAccount) {
    return RegisteredClient.withId(applicationAccount.getId().toString())
            .clientId(applicationAccount.getId().toString())
            .clientSecret(applicationAccount.getSecret())
            .authorizationGrantType(new AuthorizationGrantType("applicationAccount"))
            .scope("application")
            .redirectUri("http://localhost:8080")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .build();
  }
  */
}
