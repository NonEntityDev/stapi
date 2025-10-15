package dev.nonentity.stapi.account.controller;

import dev.nonentity.stapi.account.contract.CreateUserAccount;
import dev.nonentity.stapi.account.contract.ExistingUserAccount;
import dev.nonentity.stapi.account.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserAccountController {

  private final UserAccountService userAccountService;

  public UserAccountController(UserAccountService userAccountService) {
    this.userAccountService = userAccountService;
  }

  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Set<ExistingUserAccount>> retrieveAllUserAccounts() {
    log.info("Received request to retrieve all existing user accounts.");
    return ResponseEntity.ok(
            this.userAccountService.findAll()
    );
  }

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<ExistingUserAccount> createUserAccount(@Valid @RequestBody CreateUserAccount request) {
    log.info("Received request to create a new user account.");
    return ResponseEntity.ok(this.userAccountService.createUserAccount(request));
  }

  @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<ExistingUserAccount> findUserAccount(@PathVariable("id") UUID userAccountId) {
    log.info("Received request to retrieve an existing user account using its id.");
    return this.userAccountService.findById(userAccountId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

}
