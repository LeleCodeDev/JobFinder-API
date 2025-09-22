package com.jobFinder.be.dto.company;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequest {

  @NotNull(message = "Owner id is required")
  @Positive(message = "Owner id must be a positive number")
  private Long ownerId;

  @NotBlank(message = "Name is required")
  private String name;

  private String description;

  private String website;

  private String logo;

  private String banner;

  private String location;

  private String phone;
}
