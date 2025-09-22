package com.jobFinder.be.dto.job;

import com.jobFinder.be.model.Job;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobRequest {

  @NotBlank(message = "Title is required")
  private String title;

  private String description;

  @NotBlank(message = "Location is required")
  private String location;

  @NotBlank(message = "Currency is required")
  private String currency;

  @NotNull(message = "Minimal salary is required")
  private Integer salaryMin;

  private Integer salaryMax;

  @NotNull(message = "Category id is required")
  private Long categoryId;

  private Job.JobType type;

  private Integer applicationsCount;
}
