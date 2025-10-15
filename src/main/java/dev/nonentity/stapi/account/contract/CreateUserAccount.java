package dev.nonentity.stapi.account.contract;

import dev.nonentity.stapi.account.domain.UserAccount;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateUserAccount extends BaseUserAccount {

  @NotNull
  @Length(min = 8, max = 40)
  private String password;

  public UserAccount toEntity() {
    UserAccount userAccount = new UserAccount();
    userAccount.setName(this.getFullName());
    userAccount.setAlias(this.getLogin());
    userAccount.setEnabled(true);

    LocalDateTime now = LocalDateTime.now();
    userAccount.setCreatedAt(now);
    userAccount.setUpdatedAt(now);

    return userAccount;
  }

}
