package dev.nonentity.stapi.client.contract;

import dev.nonentity.stapi.client.domain.ClientAccount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateClientAccount extends BasicClientAccount {

  @NotEmpty
  private Set<String> scopes;

  @NotBlank
  @Length(min = 8, max = 40)
  private String clientSecret;

  public ClientAccount toEntity() {
    ClientAccount entity = new ClientAccount();
    entity.setTitle(this.getTitle());
    entity.setDescription(this.getDescription());
    entity.setAlias(this.getAlias());
    entity.setScopes(String.join(";", this.getScopes()));
    entity.setSystemAccount(false);

    LocalDateTime now = LocalDateTime.now();
    entity.setCreatedAt(now);
    entity.setUpdatedAt(now);
    return entity;
  }

}
