package com.jobFinder.be.dto.company;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PasswordCompanyRequest {

  @NotBlank(message = "Password is required")
  private String password;
}
