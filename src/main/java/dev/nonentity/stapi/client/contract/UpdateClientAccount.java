package dev.nonentity.stapi.client.contract;

import dev.nonentity.stapi.client.domain.ClientAccount;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateClientAccount extends BasicClientAccount {

  public void mergeWith(ClientAccount entity) {
    entity.setTitle(this.getTitle());
    entity.setDescription(this.getDescription());
    entity.setAlias(this.getAlias());
    entity.setUpdatedAt(LocalDateTime.now());
  }

}
