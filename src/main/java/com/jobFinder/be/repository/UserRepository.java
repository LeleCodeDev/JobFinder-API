package com.jobFinder.be.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobFinder.be.enums.UserRole;
import com.jobFinder.be.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  long countByRole(UserRole role);
}
