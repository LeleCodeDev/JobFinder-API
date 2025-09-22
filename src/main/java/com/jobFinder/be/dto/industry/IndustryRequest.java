package com.jobFinder.be.dto.industry;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IndustryRequest {

  @NotBlank(message = "name is required")
  private String name;
}
