package dev.nonentity.stapi.client.contract;

import dev.nonentity.stapi.client.domain.ClientAccount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UpdateClientAccountCredentials {

  @NotEmpty
  private Set<String> scopes;

  @NotBlank
  @Length(min = 3, max = 40)
  private String secret;

  public void mergeWith(ClientAccount entity) {
    entity.setScopes(String.join(";", this.getScopes()));
    entity.setUpdatedAt(LocalDateTime.now());
  }

}
