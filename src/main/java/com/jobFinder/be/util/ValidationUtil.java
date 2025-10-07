package com.jobFinder.be.util;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.jobFinder.be.exception.ValidationException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ValidationUtil {

  private final Validator validator;

  public <T> void validateOrThrow(T request) {
    Set<ConstraintViolation<T>> violations = validator.validate(request);

    if (!violations.isEmpty()) {
      Map<String, String> errors = violations.stream()
          .collect(Collectors.toMap(v -> v.getPropertyPath().toString(), ConstraintViolation::getMessage));

      throw new ValidationException(errors);
    }
  }

}
