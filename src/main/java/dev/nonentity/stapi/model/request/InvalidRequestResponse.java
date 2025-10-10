package dev.nonentity.stapi.model.request;

import java.util.Set;

public record InvalidRequestResponse(String type, Set<RequestValidationViolation> violations) {
}
