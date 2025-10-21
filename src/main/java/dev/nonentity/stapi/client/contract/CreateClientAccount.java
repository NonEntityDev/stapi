package dev.nonentity.stapi.client.contract;

import dev.nonentity.stapi.client.domain.ClientAccount;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateClientAccount extends BasicClientAccount {

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
