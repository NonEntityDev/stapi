package dev.nonentity.stapi.model.request;

import java.util.Set;

public record RequestValidationViolation(String field, Set<String> messages) {
}
