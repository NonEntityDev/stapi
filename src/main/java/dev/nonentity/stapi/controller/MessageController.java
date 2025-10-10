package dev.nonentity.stapi.controller;

import dev.nonentity.stapi.model.request.SendMessageRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/message")
@Slf4j
public class MessageController {

  @PostMapping
  public ResponseEntity<Void> sendMessage(@Valid SendMessageRequest request) {
      return ResponseEntity.accepted().build();
  }

}
