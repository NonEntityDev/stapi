package dev.nonentity.stapi.client.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class ClientParameter {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID parameterId;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private ClientAccount clientAccount;

  @Length(min = 3, max = 40)
  @Pattern(regexp = "^[a-zA-Z0-9\\-_.]*$")
  @Column(name = "parameter_name")
  private String name;

  @NotBlank
  @Length(max = 140)
  @Column(name = "parameter_value")
  private String value;

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime modifiedAt;

}
