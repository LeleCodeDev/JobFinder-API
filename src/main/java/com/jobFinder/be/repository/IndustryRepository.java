package com.jobFinder.be.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobFinder.be.model.Industry;

public interface IndustryRepository extends JpaRepository<Industry, Long> {
  Optional<Industry> findByName(String name);
}
