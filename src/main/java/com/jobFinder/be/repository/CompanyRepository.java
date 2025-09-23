package com.jobFinder.be.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobFinder.be.model.Company;
import com.jobFinder.be.model.User;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

  Optional<Company> findByName(String name);

  boolean existsByOwner(User owner);

  Optional<Company> findByOwnerId(Long ownerId);

}
