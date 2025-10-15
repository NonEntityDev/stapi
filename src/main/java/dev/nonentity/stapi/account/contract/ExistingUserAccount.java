package dev.nonentity.stapi.account.contract;

import dev.nonentity.stapi.account.domain.UserAccount;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExistingUserAccount extends BaseUserAccount {

  private UUID id;

  private Boolean enabled;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public static ExistingUserAccount fromEntity(UserAccount entity) {
    ExistingUserAccount existingUserAccount = new ExistingUserAccount();
    existingUserAccount.setId(entity.getId());
    existingUserAccount.setFullName(entity.getName());
    existingUserAccount.setLogin(entity.getAlias());
    existingUserAccount.setEnabled(entity.getEnabled());
    existingUserAccount.setCreatedAt(entity.getCreatedAt());
    existingUserAccount.setUpdatedAt(entity.getUpdatedAt());
    return existingUserAccount;
  }

}
