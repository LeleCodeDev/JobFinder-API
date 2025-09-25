package com.jobFinder.be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobFinder.be.enums.ActiveStatus;
import com.jobFinder.be.model.Industry;

public interface IndustryRepository extends JpaRepository<Industry, Long> {
  Optional<Industry> findByName(String name);

  List<Industry> findAllByStatus(ActiveStatus status);
}
