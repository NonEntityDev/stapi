package dev.nonentity.stapi.client.contract.validation;

import dev.nonentity.stapi.client.contract.CreateClientParameter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UniqueClientParametersValidator implements ConstraintValidator<UniqueClientParameters, Collection<CreateClientParameter>> {


  @Override
  public boolean isValid(Collection<CreateClientParameter> clientParameters, ConstraintValidatorContext constraintValidatorContext) {
    if ((clientParameters != null) && (!clientParameters.isEmpty())) {
      Map<String, List<CreateClientParameter>> parametersByName = clientParameters.stream()
              .collect(Collectors.groupingBy(
                      CreateClientParameter::getName, Collectors.toList()
              ));

      Set<String> duplicatedParameters = parametersByName.values()
              .stream()
              .filter((List<CreateClientParameter> parametersWithSameName) -> parametersWithSameName.size() > 1)
              .map((List<CreateClientParameter> parameters) -> parameters.get(0).getName())
              .sorted()
              .collect(Collectors.toCollection(LinkedHashSet::new));

      if (!duplicatedParameters.isEmpty()) {
        String duplicatedParametersNames = String.join(",", duplicatedParameters);

        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(
                constraintValidatorContext.getDefaultConstraintMessageTemplate().replace("{duplicatedParameters}", duplicatedParametersNames)
        ).addConstraintViolation();

        return false;
      }
    }

    return true;
  }
}
