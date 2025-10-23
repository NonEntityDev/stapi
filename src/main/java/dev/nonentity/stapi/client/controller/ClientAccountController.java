package dev.nonentity.stapi.client.controller;

import dev.nonentity.stapi.client.contract.CreateClientAccount;
import dev.nonentity.stapi.client.contract.ExistingClientAccount;
import dev.nonentity.stapi.client.contract.UpdateClientAccount;
import dev.nonentity.stapi.client.contract.UpdateClientAccountCredentials;
import dev.nonentity.stapi.client.service.ClientAccountService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clients")
@Slf4j
public class ClientAccountController {

  private final ClientAccountService clientAccountService;

  public ClientAccountController(ClientAccountService clientAccountService) {
    this.clientAccountService = clientAccountService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ExistingClientAccount> create(@Valid @RequestBody CreateClientAccount request) {
    log.info("Processing request to create a new client account.");
    return ResponseEntity.ok(
            this.clientAccountService.create(request)
    );
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Set<ExistingClientAccount>> listAll() {
    log.info("Processing request to retrieve all available client accounts.");
    return ResponseEntity.ok(
            this.clientAccountService.loadAll()
    );
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> update(@PathVariable("id") UUID clientId, @Valid @RequestBody UpdateClientAccount request) {
    log.info("Processing request to update client account details.");
    Optional<ExistingClientAccount> updateClientAccount = this.clientAccountService.update(clientId, request);
    if (updateClientAccount.isPresent()) {
      return ResponseEntity.ok(updateClientAccount.get());

    } else {
      return ResponseEntity
              .status(HttpStatus.NOT_FOUND)
              .body(Map.of(
                      "message", "Client account not found."
              ));

    }
  }

  @PutMapping(value = "/{id}/credentials", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> updateCredentials(@PathVariable("id") UUID clientId, @Valid @RequestBody UpdateClientAccountCredentials request) {
    log.info("Processing request to update client account credentials.");
    Optional<ExistingClientAccount> updatedClientAccount = this.clientAccountService.updateCredentials(clientId, request);
    if (updatedClientAccount.isPresent()) {
      return ResponseEntity.ok(updatedClientAccount.get());

    } else {
      return ResponseEntity
              .status(HttpStatus.NOT_FOUND)
              .body(Map.of(
                      "message", "Client account not found."
              ));

    }
  }

  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> remove(@PathVariable("id") UUID clientAccountId) {
    log.info("Processing request to delete an existing client account.");
    Optional<ExistingClientAccount> removedClientAccount = this.clientAccountService.remove(clientAccountId);
    if (removedClientAccount.isPresent()) {
      return ResponseEntity.ok(removedClientAccount.get());

    } else {
      return ResponseEntity
              .status(HttpStatus.NOT_FOUND)
              .body(Map.of(
                      "message", "Client account not found."
              ));
    }
  }

}
