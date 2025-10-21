package dev.nonentity.stapi.client.contract;

import dev.nonentity.stapi.client.domain.ClientAccount;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExistingClientAccountCredentials extends ExistingClientAccount {

  private Set<String> scopes;

  private String clientSecret;

  public ExistingClientAccountCredentials(ClientAccount entity) {
    super(entity);
    this.setScopes(
            Set.of(entity.getScopes().split(";"))
    );
    this.setClientSecret(entity.getSecretHash());
  }

}
