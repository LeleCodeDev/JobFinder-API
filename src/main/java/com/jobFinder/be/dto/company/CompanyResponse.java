package com.jobFinder.be.dto.company;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jobFinder.be.dto.employee.EmployeeCompanyResponse;
import com.jobFinder.be.dto.industry.IndustryResponse;
import com.jobFinder.be.enums.ActiveStatus;
import com.jobFinder.be.model.Employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CompanyResponse {

  private Long id;

  private String name;

  private String description;

  private String website;

  private String logo;

  private String banner;

  private String location;

  private String phone;

  private IndustryResponse industry;

  private LocalDate foundedDate;

  private Boolean verified;

  private ActiveStatus status;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updatedAt;
}
