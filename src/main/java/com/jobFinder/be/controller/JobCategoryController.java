package com.jobFinder.be.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobFinder.be.dto.WebResponse;
import com.jobFinder.be.dto.jobCategory.JobCategoryRequest;
import com.jobFinder.be.dto.jobCategory.JobCategoryResponse;
import com.jobFinder.be.service.JobCategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/job-categories")
@RequiredArgsConstructor
public class JobCategoryController {

  private final JobCategoryService jobCategoryService;

  @GetMapping
  @PreAuthorize("permitAll()")
  public ResponseEntity<WebResponse<List<JobCategoryResponse>>> getAllJobCategories() {
    List<JobCategoryResponse> jobCategories = jobCategoryService.getAll();
    WebResponse<List<JobCategoryResponse>> response = WebResponse.success("All job categories successfully fetched",
        jobCategories);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN')")
  public ResponseEntity<WebResponse<JobCategoryResponse>> createJobCategory(
      @RequestBody JobCategoryRequest request) {
    JobCategoryResponse jobCategory = jobCategoryService.create(request);
    WebResponse<JobCategoryResponse> response = WebResponse.success("Job category created successfully", jobCategory);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN')")
  public ResponseEntity<WebResponse<JobCategoryResponse>> updateJobCategory(@PathVariable Long id,
      @RequestBody JobCategoryRequest request) {

    JobCategoryResponse jobCategory = jobCategoryService.update(id, request);
    WebResponse<JobCategoryResponse> response = WebResponse.success("Job category updated successfully", jobCategory);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}/deactivate")
  @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN')")
  public ResponseEntity<WebResponse<JobCategoryResponse>> deactivateJobCategory(@PathVariable Long id) {
    JobCategoryResponse jobCategory = jobCategoryService.deactivate(id);
    WebResponse<JobCategoryResponse> response = WebResponse.success("Job category deactivated successfully",
        jobCategory);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}/activate")
  @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN')")
  public ResponseEntity<WebResponse<JobCategoryResponse>> activateJobCategory(@PathVariable Long id) {
    JobCategoryResponse jobCategory = jobCategoryService.activate(id);
    WebResponse<JobCategoryResponse> response = WebResponse.success("Job category activated successfully", jobCategory);
    return ResponseEntity.ok(response);
  }
}
