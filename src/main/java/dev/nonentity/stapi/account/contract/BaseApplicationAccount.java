package dev.nonentity.stapi.account.contract;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public abstract class BaseApplicationAccount {

  @NotNull
  @Length(min = 3, max = 140)
  private String name;

  @NotNull
  @Length(min = 3, max = 40)
  @Pattern(regexp = "^[a-zA-Z_\\-]*$")
  private String alias;


}
