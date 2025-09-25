package com.jobFinder.be.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jobFinder.be.enums.ActiveStatus;
import com.jobFinder.be.enums.BusinessRole;
import com.jobFinder.be.model.Employee;

import jakarta.transaction.Transactional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  Optional<Employee> findByUserIdAndRole(Long userId, BusinessRole role);

  boolean existsByUserIdAndCompanyIdAndRole(Long userId, Long companyId, BusinessRole role);

  Optional<Employee> findByUserIdAndCompanyId(Long userId, Long companyId);

  boolean existsByUserIdAndCompanyId(Long userId, Long companyId);

  boolean existsByIdAndStatus(Long id, ActiveStatus status);

  @Modifying
  @Transactional
  @Query("UPDATE Employee e " +
      "SET e.status = :status " +
      "WHERE e.company.id = :companyId AND e.role <> :ownerRole")
  void updateAllStatusByCompanyIdExcept(@Param("companyId") Long companyId, @Param("status") ActiveStatus status,
      @Param("ownerRole") BusinessRole ownerRole);

  @Modifying
  @Transactional
  @Query("UPDATE Employee e " +
      "SET e.status = :status " +
      "WHERE e.company.id = :companyId")
  void updateAllStatusByCompanyId(@Param("companyId") Long companyId, @Param("status") ActiveStatus status);
}
