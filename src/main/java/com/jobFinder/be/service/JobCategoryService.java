package com.jobFinder.be.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobFinder.be.dto.jobCategory.JobCategoryRequest;
import com.jobFinder.be.dto.jobCategory.JobCategoryResponse;
import com.jobFinder.be.enums.ActiveStatus;
import com.jobFinder.be.exception.ResourceAlreadyTakenException;
import com.jobFinder.be.exception.ResourceNotFoundException;
import com.jobFinder.be.mapper.JobCategoryMapper;
import com.jobFinder.be.model.JobCategory;
import com.jobFinder.be.model.User;
import com.jobFinder.be.repository.JobCategoryRepository;
import com.jobFinder.be.util.UserUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobCategoryService {

  private final JobCategoryRepository jobCategoryRepository;
  private final JobCategoryMapper jobCategoryMapper;
  private final UserUtil userUtil;

  @Transactional
  public JobCategoryResponse create(JobCategoryRequest request) {
    jobCategoryRepository.findByName(request.getName()).ifPresent(jc -> {
      throw new ResourceAlreadyTakenException("Job category already exists");
    });

    JobCategory jobCategory = jobCategoryMapper.toEntity(request);

    JobCategory savedJobCategory = jobCategoryRepository.save(jobCategory);

    return jobCategoryMapper.toResponse(savedJobCategory);
  }

  @Transactional
  public JobCategoryResponse update(Long id, JobCategoryRequest request) {
    JobCategory jobCategory = jobCategoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Job category not found with ID; " + id));

    if (!jobCategory.getName().equals(request.getName())) {

      jobCategoryRepository.findByName(request.getName()).ifPresent(jc -> {
        throw new ResourceAlreadyTakenException("Job category already exists");
      });
    }

    jobCategoryMapper.updateEntity(jobCategory, request);

    JobCategory updatedJobCategory = jobCategoryRepository.save(jobCategory);

    return jobCategoryMapper.toResponse(updatedJobCategory);
  }

  @Transactional(readOnly = true)
  public List<JobCategoryResponse> getAll() {
    User user = userUtil.getAuthenticatedUserOrNull();

    List<JobCategory> jobCategories = user != null && userUtil.isAdmin(user)
        ? jobCategoryRepository.findAll()
        : jobCategoryRepository.findAllByStatus(ActiveStatus.ACTIVE);

    return jobCategories.stream()
        .map(jobCategoryMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional
  public JobCategoryResponse deactivate(Long id) {
    JobCategory jobCategory = jobCategoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Job category not found with ID: " + id));

    jobCategory.setStatus(ActiveStatus.INACTIVE);
    JobCategory updatedJobCategory = jobCategoryRepository.save(jobCategory);

    return jobCategoryMapper.toResponse(updatedJobCategory);
  }

  @Transactional
  public JobCategoryResponse activate(Long id) {
    JobCategory jobCategory = jobCategoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Job category not found with ID: " + id));

    jobCategory.setStatus(ActiveStatus.ACTIVE);
    JobCategory updatedJobCategory = jobCategoryRepository.save(jobCategory);

    return jobCategoryMapper.toResponse(updatedJobCategory);
  }
}
