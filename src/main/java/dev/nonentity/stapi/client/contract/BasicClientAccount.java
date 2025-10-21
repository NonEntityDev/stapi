package dev.nonentity.stapi.client.contract;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Data
public abstract class BasicClientAccount {

  @NotNull
  @Length(min = 3, max = 140)
  private String title;

  @NotNull
  @Length(min = 3, max = 140)
  private String description;

  @NotNull
  @Length(min = 3, max = 40)
  private String alias;


}
