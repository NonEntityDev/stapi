package dev.nonentity.stapi.account.contract;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public abstract class BaseUserAccount {

  @NotNull
  @Length(min = 3, max = 140)
  String fullName;

  @NotNull
  @Length(min = 3, max = 140)
  @Email
  String login;

}
