package com.jobFinder.be.exception;

public class InactiveResourceException extends RuntimeException {
  public InactiveResourceException(String message) {
    super(message);
  }
}
