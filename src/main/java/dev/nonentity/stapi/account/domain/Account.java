package dev.nonentity.stapi.account.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Account {

  @Id
  @GeneratedValue(strategy=GenerationType.UUID)
  private UUID id;

  @NotNull
  @Length(min = 3, max = 140)
  private String name;

  @NotNull
  @Length(min = 3, max = 140)
  private String alias;

  @NotNull
  private Boolean enabled;

  @NotBlank
  private String secret;

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime updatedAt;

}
