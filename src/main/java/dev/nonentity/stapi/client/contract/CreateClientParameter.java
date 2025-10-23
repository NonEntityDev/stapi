package dev.nonentity.stapi.client.contract;

import dev.nonentity.stapi.client.domain.ClientParameter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateClientParameter extends BasicClientParameter {

  public ClientParameter toEntity() {
    ClientParameter parameter = new ClientParameter();
    parameter.setName(this.getName());
    parameter.setValue(this.getValue());

    parameter.setModifiedAt(LocalDateTime.now());
    return parameter;
  }

}
