package com.jobFinder.be.dto.jobCategory;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobCategoryRequest {

  @NotBlank(message = "name is required")
  private String name;
}
