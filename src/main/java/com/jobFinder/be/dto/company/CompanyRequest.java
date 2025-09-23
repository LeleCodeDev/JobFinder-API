package com.jobFinder.be.dto.company;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequest {

  @NotBlank(message = "Name is required")
  private String name;

  private String description;

  private String website;

  private String logo;

  private String banner;

  @NotBlank(message = "Location is required")
  private String location;

  @NotBlank(message = "Phone is required")
  private String phone;

  @PastOrPresent(message = "Founded date cannot be in the future")
  private LocalDate foundedDate;

  @NotNull(message = "Industry id is required")
  @Positive(message = "Industry id must be a positive number")
  private Long industryId;
}
