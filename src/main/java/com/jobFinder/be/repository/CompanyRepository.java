package com.jobFinder.be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobFinder.be.enums.ActiveStatus;
import com.jobFinder.be.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

  Optional<Company> findByName(String name);

  boolean existsByIdAndStatus(Long id, ActiveStatus status);

  Optional<Company> findByIdAndStatus(Long id, ActiveStatus status);

  List<Company> findAllByStatus(ActiveStatus status);
}
