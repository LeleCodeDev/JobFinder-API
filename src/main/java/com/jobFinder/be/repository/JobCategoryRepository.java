package com.jobFinder.be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobFinder.be.enums.ActiveStatus;
import com.jobFinder.be.model.JobCategory;

@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {

  Optional<JobCategory> findByName(String name);

  List<JobCategory> findAllByStatus(ActiveStatus status);
}
