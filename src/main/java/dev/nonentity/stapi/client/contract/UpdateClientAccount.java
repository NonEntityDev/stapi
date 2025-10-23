package dev.nonentity.stapi.client.contract;

import dev.nonentity.stapi.client.domain.ClientAccount;
import dev.nonentity.stapi.client.domain.ClientParameter;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateClientAccount extends BasicClientAccount {

  @NotEmpty
  private Set<CreateClientParameter> parameters;

  public void mergeWith(ClientAccount entity) {
    entity.setTitle(this.getTitle());
    entity.setDescription(this.getDescription());
    entity.setAlias(this.getAlias());

    entity.getParameters().clear();
    for (CreateClientParameter parameter: this.getParameters()) {
      ClientParameter clientParameter = parameter.toEntity();
      clientParameter.setClientAccount(entity);
      entity.getParameters().add(clientParameter);
    }

    entity.setUpdatedAt(LocalDateTime.now());
  }

}
