package dev.nonentity.stapi.account.controller;

import dev.nonentity.stapi.account.contract.CreateApplicationAccount;
import dev.nonentity.stapi.account.contract.ExistingApplicationAccount;
import dev.nonentity.stapi.account.contract.ExistingApplicationAccountCredentials;
import dev.nonentity.stapi.account.service.ApplicationAccountService;
import jakarta.persistence.SecondaryTable;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/application")
@Slf4j
public class ApplicationAccountController {

  private final ApplicationAccountService applicationAccountService;

  public ApplicationAccountController(ApplicationAccountService applicationAccountService) {
    this.applicationAccountService = applicationAccountService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ExistingApplicationAccountCredentials> createApplicationAccount(@Valid @RequestBody CreateApplicationAccount request) {
    log.info("Received request to create a new application account.");
    return ResponseEntity.ok(this.applicationAccountService.createApplicationAccount(request));
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ExistingApplicationAccount> findApplicationAccount(@PathVariable("id") UUID applicationAccountId) {
    log.info("Received request to retrieve an existing application account using its id.");
    return this.applicationAccountService.findById(applicationAccountId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Set<ExistingApplicationAccount>> retrieveAllApplicationAccounts() {
    log.info("Received request to retrieve all existing application accounts.");
    return ResponseEntity.ok(
            this.applicationAccountService.findAll()
    );
  }

  @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<ExistingApplicationAccount> deleteApplicationAccount(@PathVariable("id") UUID applicationAccountId) {
    log.info("Received request to delete application account.");
    return this.applicationAccountService.removeApplicationAccount(applicationAccountId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

}
