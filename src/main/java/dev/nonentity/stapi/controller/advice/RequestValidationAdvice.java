package dev.nonentity.stapi.controller.advice;

import dev.nonentity.stapi.model.request.InvalidRequestResponse;
import dev.nonentity.stapi.model.request.RequestValidationViolation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class RequestValidationAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<InvalidRequestResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
    BindingResult validationResult = exception.getBindingResult();

    Set<RequestValidationViolation> violations = validationResult.getFieldErrors().stream()
            .collect(
                    Collectors.groupingBy(FieldError::getField)
            )
            .entrySet()
            .stream()
            .map((Map.Entry<String, List<FieldError>> errorsPerField) -> new RequestValidationViolation(errorsPerField.getKey(), errorsPerField.getValue().stream().map(FieldError::getDefaultMessage).collect(Collectors.toSet())))
            .collect(Collectors.toSet());

    InvalidRequestResponse response = new InvalidRequestResponse(validationResult.getObjectName(), violations);
    return ResponseEntity.badRequest().body(response);
  }

}
