package dev.nonentity.stapi.account.contract;

import dev.nonentity.stapi.account.domain.ApplicationAccount;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateApplicationAccount extends BaseApplicationAccount {

  public ApplicationAccount toEntity() {
    ApplicationAccount applicationAccount = new ApplicationAccount();
    applicationAccount.setName(this.getName());
    applicationAccount.setAlias(this.getAlias());
    applicationAccount.setEnabled(true);

    LocalDateTime now = LocalDateTime.now();
    applicationAccount.setCreatedAt(now);
    applicationAccount.setUpdatedAt(now);
    return applicationAccount;
  }

}
