package dev.nonentity.stapi.client.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class ClientAccount {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID clientId;

  @NotNull
  @Length(min = 3, max = 140)
  private String title;

  @NotNull
  @Length(min = 3, max = 140)
  private String description;

  @NotNull
  @Length(min = 3, max = 40)
  @Pattern(regexp = "^[a-zA-Z0-9\\-_.]*$")
  private String alias;

  @NotNull
  private String secretHash;

  @NotBlank
  @Length(max = 140)
  private String scopes;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "clientAccount")
  private List<ClientParameter> parameters;

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime updatedAt;

}
