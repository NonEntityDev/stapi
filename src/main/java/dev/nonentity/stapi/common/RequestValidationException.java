package dev.nonentity.stapi.common;

import lombok.Getter;

@Getter
public class RequestValidationException extends RuntimeException {

  private final String requestType;
  private final String field;
  private final String message;

  public RequestValidationException(String requestType, String field, String message) {
    this.requestType = requestType;
    this.field = field;
    this.message = message;
  }

}
