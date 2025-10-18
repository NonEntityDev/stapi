package dev.nonentity.stapi.account.controller;

import dev.nonentity.stapi.account.contract.CreateApplicationAccount;
import dev.nonentity.stapi.account.contract.ExistingApplicationAccountCredentials;
import dev.nonentity.stapi.account.service.ApplicationAccountService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
