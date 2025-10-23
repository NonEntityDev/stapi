package dev.nonentity.stapi.client.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.nonentity.stapi.client.domain.ClientParameter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExistingClientParameter extends BasicClientParameter {

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime modifiedAt;

  public ExistingClientParameter(ClientParameter entity) {
    this.setName(entity.getName());
    this.setValue(entity.getValue());
    this.setModifiedAt(entity.getModifiedAt());
  }

}
