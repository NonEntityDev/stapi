package dev.nonentity.stapi.client.controller;

import dev.nonentity.stapi.client.contract.CreateClientAccount;
import dev.nonentity.stapi.client.contract.ExistingClientAccountCredentials;
import dev.nonentity.stapi.client.service.ClientAccountService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clients")
@Slf4j
public class ClientAccountController {

  private final ClientAccountService clientAccountService;

  public ClientAccountController(ClientAccountService clientAccountService) {
    this.clientAccountService = clientAccountService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ExistingClientAccountCredentials> create(@Valid @RequestBody CreateClientAccount request) {
    log.info("Processing request to create a new client account.");
    return ResponseEntity.ok(
            this.clientAccountService.create(request)
    );
  }

}
