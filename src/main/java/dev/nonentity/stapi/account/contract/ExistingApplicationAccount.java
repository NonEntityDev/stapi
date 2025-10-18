package dev.nonentity.stapi.account.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.nonentity.stapi.account.domain.ApplicationAccount;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExistingApplicationAccount extends BaseApplicationAccount {

  private UUID clientId;

  private Boolean enabled;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updatedAt;

  public static ExistingApplicationAccount fromEntity(ApplicationAccount entity) {
    ExistingApplicationAccount applicationAccount = new ExistingApplicationAccount();
    applicationAccount.setClientId(entity.getId());
    applicationAccount.setName(entity.getName());
    applicationAccount.setAlias(entity.getAlias());
    applicationAccount.setEnabled(entity.getEnabled());
    applicationAccount.setCreatedAt(entity.getCreatedAt());
    applicationAccount.setUpdatedAt(entity.getUpdatedAt());
    return applicationAccount;
  }

}
