package dev.nonentity.stapi.client.contract;

import dev.nonentity.stapi.client.domain.ClientAccount;
import dev.nonentity.stapi.client.domain.ClientParameter;
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

  @NotEmpty
  private Set<CreateClientParameter> parameters;

  @NotBlank
  @Length(min = 8, max = 40)
  private String clientSecret;

  public ClientAccount toEntity() {
    ClientAccount entity = new ClientAccount();
    entity.setTitle(this.getTitle());
    entity.setDescription(this.getDescription());
    entity.setAlias(this.getAlias());
    entity.setScopes(String.join(";", this.getScopes()));

    entity.setParameters(
            this.getParameters()
                    .stream()
                    .map(CreateClientParameter::toEntity)
                    .peek((ClientParameter parameter) -> parameter.setClientAccount(entity))
                    .toList()
    );

    LocalDateTime now = LocalDateTime.now();
    entity.setCreatedAt(now);
    entity.setUpdatedAt(now);
    return entity;
  }

}
