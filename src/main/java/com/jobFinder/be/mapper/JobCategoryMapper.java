package com.jobFinder.be.mapper;

import org.springframework.stereotype.Component;

import com.jobFinder.be.dto.jobCategory.JobCategoryRequest;
import com.jobFinder.be.dto.jobCategory.JobCategoryResponse;
import com.jobFinder.be.enums.ActiveStatus;
import com.jobFinder.be.model.JobCategory;

@Component
public class JobCategoryMapper {

  public JobCategory toEntity(JobCategoryRequest request) {
    return JobCategory.builder()
        .name(request.getName())
        .status(ActiveStatus.ACTIVE)
        .build();
  }

  public JobCategoryResponse toResponse(JobCategory jobCategory) {
    return JobCategoryResponse.builder()
        .id(jobCategory.getId())
        .name(jobCategory.getName())
        .status(jobCategory.getStatus())
        .createdAt(jobCategory.getCreatedAt())
        .updatedAt(jobCategory.getUpdatedAt())
        .build();
  }

  public void updateEntity(JobCategory existingJobCategory, JobCategoryRequest request) {
    existingJobCategory.setName(request.getName());
  }

}
