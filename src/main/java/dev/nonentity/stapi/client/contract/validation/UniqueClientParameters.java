package dev.nonentity.stapi.client.contract.validation;

import com.nimbusds.jose.Payload;
import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UniqueClientParametersValidator.class)
public @interface UniqueClientParameters {

  Class<?>[] groups() default {};

  String message() default "duplicated parameters: {duplicatedParameters}" ;

  Class<? extends Payload>[] payload() default {};

}
