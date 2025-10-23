package dev.nonentity.stapi.client.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.nonentity.stapi.client.domain.ClientAccount;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExistingClientAccount extends BasicClientAccount {

  private UUID clientId;

  private Set<ExistingClientParameter> parameters;

  private Set<String> scopes;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updatedAt;

  public ExistingClientAccount(ClientAccount entity) {
    this.setClientId(entity.getClientId());
    this.setTitle(entity.getTitle());
    this.setDescription(entity.getDescription());
    this.setAlias(entity.getAlias());
    this.setScopes(Set.of(entity.getScopes().split(";")));
    this.setParameters(
            entity.getParameters()
                    .stream()
                    .map(ExistingClientParameter::new)
                    .collect(Collectors.toSet())
    );
    this.setCreatedAt(entity.getCreatedAt());
    this.setUpdatedAt(entity.getUpdatedAt());
  }

}
