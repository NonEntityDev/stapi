package dev.nonentity.stapi.client.contract;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public abstract class BasicClientParameter {

  @Length(min = 3, max = 40)
  @Pattern(regexp = "^[a-zA-Z0-9\\-_.]*$")
  private String name;

  @NotBlank
  @Length(max = 140)
  private String value;

}
