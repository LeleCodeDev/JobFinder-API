package com.jobFinder.be.dto.job;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jobFinder.be.model.Company;
import com.jobFinder.be.model.Job;
import com.jobFinder.be.model.JobCategory;
import com.jobFinder.be.model.JobQualification;
import com.jobFinder.be.model.JobSkill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobResponse {

  private Long id;

  private Company company;

  private String title;

  private String description;

  private String location;

  private String currency;

  private Integer salaryMin;

  private Integer salaryMax;

  private JobCategory category;

  private Job.JobStatus status;

  private Job.JobType type;

  private Integer applicationsCount;

  private List<JobQualification> qualifications;

  private List<JobSkill> skills;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updatedAt;
}
