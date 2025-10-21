package dev.nonentity.stapi.client.contract;

import dev.nonentity.stapi.client.domain.ClientAccount;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExistingClientAccountCredentials extends ExistingClientAccount {

  private String clientSecret;

  public ExistingClientAccountCredentials(ClientAccount entity) {
    super(entity);

    byte[] rawClientSecret = entity.getSecretHash().getBytes(StandardCharsets.UTF_8);
    String encodedClientSecret = Base64.getUrlEncoder().withoutPadding().encodeToString(rawClientSecret);
    this.setClientSecret(encodedClientSecret);
  }

}
