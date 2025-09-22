package com.jobFinder.be.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jobFinder.be.dto.WebResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<WebResponse<String>> handleResourceNotFoundException(ResourceNotFoundException exception) {
    WebResponse<String> response = WebResponse.error(exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<WebResponse<Map<String, String>>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {
    Map<String, String> errors = new HashMap<>();
    exception.getBindingResult().getFieldErrors().forEach(error -> {
      errors.put(error.getField(), error.getDefaultMessage());
    });

    WebResponse<Map<String, String>> response = WebResponse.error("Validation failed", errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity<WebResponse<String>> handleInvalidTokenException(InvalidTokenException exception) {
    WebResponse<String> response = WebResponse.error(exception.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }

  @ExceptionHandler(ResourceAlreadyTakenException.class)
  public ResponseEntity<WebResponse<String>> handleResourceAlreadyTaken(ResourceAlreadyTakenException exception) {
    WebResponse<String> response = WebResponse.error(exception.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<WebResponse<String>> handleBadCredentialExceprion(BadCredentialsException exception) {
    WebResponse<String> response = WebResponse.error("Incorrect username or password");
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<WebResponse<String>> handleRuntime(RuntimeException exception) {
    log.warn("Runtime exception occurred: {}", exception.getMessage(), exception);
    WebResponse<String> response = WebResponse.error(exception.getMessage());
    return ResponseEntity.badRequest().body(response);
  }
}
