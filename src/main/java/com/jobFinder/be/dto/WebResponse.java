package com.jobFinder.be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse<T> {
  private boolean success;
  private String message;
  private T data;

  public static <T> WebResponse<T> success(String message, T data) {
    return new WebResponse<>(true, message, data);
  }

  public static <T> WebResponse<T> success(String message) {
    return new WebResponse<>(true, message, null);
  }

  public static <T> WebResponse<T> error(String message) {
    return new WebResponse<>(false, message, null);
  }

  public static <T> WebResponse<T> error(String message, T data) {
    return new WebResponse<>(false, message, data);
  }
}
