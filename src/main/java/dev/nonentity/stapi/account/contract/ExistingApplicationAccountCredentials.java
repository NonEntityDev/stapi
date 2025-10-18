package dev.nonentity.stapi.account.contract;

import dev.nonentity.stapi.account.domain.ApplicationAccount;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExistingApplicationAccountCredentials extends ExistingApplicationAccount {

  private String clientSecret;

  public static ExistingApplicationAccountCredentials fromEntity(ApplicationAccount entity) {
    ExistingApplicationAccountCredentials credentials = new ExistingApplicationAccountCredentials();
    credentials.setClientId(entity.getId());
    credentials.setName(entity.getName());
    credentials.setAlias(entity.getAlias());
    credentials.setClientSecret(entity.getSecret());
    credentials.setEnabled(entity.getEnabled());
    credentials.setCreatedAt(entity.getCreatedAt());
    credentials.setUpdatedAt(entity.getUpdatedAt());
    return credentials;
  }


}
