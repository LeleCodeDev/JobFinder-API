package com.jobFinder.be.mapper;

import org.springframework.stereotype.Component;

import com.jobFinder.be.dto.employee.EmployeeCompanyResponse;
import com.jobFinder.be.model.Employee;

@Component
public class EmployeeMapper {

  public EmployeeCompanyResponse toCompanyResponse(Employee employee) {
    return EmployeeCompanyResponse.builder()
        .id(employee.getUser().getId())
        .username(employee.getUser().getUsername())
        .email(employee.getUser().getEmail())
        .role(employee.getRole())
        .createdAt(employee.getCreatedAt())
        .updatedAt(employee.getUpdatedAt())
        .build();

  }
}
