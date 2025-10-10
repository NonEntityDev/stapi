package dev.nonentity.stapi.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record SendMessageRequest(@NotNull @Length(min = 3, max = 140) String sender,
                                 @NotNull @Length(min = 3, max = 70) String source,
                                 @NotNull @Length(min = 3, max = 140) String subject, @NotBlank String message,
                                 @NotNull @Past LocalDateTime receivedAt) {

}
