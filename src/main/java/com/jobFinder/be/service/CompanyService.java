package com.jobFinder.be.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobFinder.be.dto.company.CompanyRequest;
import com.jobFinder.be.dto.company.CompanyResponse;
import com.jobFinder.be.enums.ActiveStatus;
import com.jobFinder.be.enums.BusinessRole;
import com.jobFinder.be.exception.ForbiddenException;
import com.jobFinder.be.exception.InactiveResourceException;
import com.jobFinder.be.exception.ResourceNotFoundException;
import com.jobFinder.be.mapper.CompanyMapper;
import com.jobFinder.be.model.Company;
import com.jobFinder.be.model.Employee;
import com.jobFinder.be.model.Industry;
import com.jobFinder.be.model.User;
import com.jobFinder.be.repository.CompanyRepository;
import com.jobFinder.be.repository.EmployeeRepository;
import com.jobFinder.be.repository.IndustryRepository;
import com.jobFinder.be.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {

  private final CompanyRepository companyRepository;
  private final UserRepository userRepository;
  private final IndustryRepository industryRepository;
  private final EmployeeRepository employeeRepository;
  private final CompanyMapper companyMapper;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public CompanyResponse create(CompanyRequest request) {
    User user = getAuthenticatedUser();

    Industry industry = industryRepository.findById(request.getIndustryId())
        .orElseThrow(() -> new ResourceNotFoundException("Industry not found with ID: " + request.getIndustryId()));

    Company company = companyMapper.toEntity(request, industry);
    Company savedCompany = companyRepository.save(company);

    Employee employee = Employee.builder()
        .user(user)
        .company(company)
        .role(BusinessRole.OWNER)
        .status(ActiveStatus.ACTIVE)
        .build();

    employeeRepository.save(employee);

    return companyMapper.toResponse(savedCompany);
  }

  @Transactional
  public CompanyResponse update(Long id, CompanyRequest request) {
    User user = getAuthenticatedUser();

    Company company = companyRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + id));

    Employee employee = employeeRepository.findByUserIdAndCompanyId(user.getId(), id)
        .orElseThrow(() -> new ForbiddenException("You are not an employee in this company"));

    if (employee.getStatus() != ActiveStatus.ACTIVE) {
      throw new InactiveResourceException("Your employee account is inactive");
    }

    if (!(employee.getRole() == BusinessRole.OWNER || employee.getRole() == BusinessRole.STAFF)) {
      throw new ForbiddenException("You are not allowed to update this company");
    }

    if (company.getStatus() != ActiveStatus.ACTIVE) {
      throw new InactiveResourceException("Company is inactive");
    }

    if (!company.getIndustry().getId().equals(request.getIndustryId())) {
      Industry industry = industryRepository.findById(request.getIndustryId())
          .orElseThrow(() -> new ResourceNotFoundException("Industry not found with ID: " + request.getIndustryId()));
      company.setIndustry(industry);
    }

    companyMapper.updateEntity(company, request);

    Company updatedCompany = companyRepository.save(company);
    return companyMapper.toResponse(updatedCompany);
  }

  @Transactional(readOnly = true)
  public List<CompanyResponse> getAll() {
    List<Company> companies = companyRepository.findAllByStatus(ActiveStatus.ACTIVE);

    return companies.stream()
        .map(companyMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public CompanyResponse getById(Long id) {
    Company company = companyRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + id));

    return companyMapper.toResponse(company);
  }

  @Transactional
  public void deactivate(Long id, String password) {
    User user = getAuthenticatedUser();

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new ForbiddenException("Invalid password");
    }

    Company company = companyRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + id));

    Employee employee = employeeRepository.findByUserIdAndCompanyId(user.getId(), id)
        .orElseThrow(() -> new ForbiddenException("You are not an employee in this company"));

    if (employee.getStatus() != ActiveStatus.ACTIVE) {
      throw new InactiveResourceException("Your employee account is inactive");
    }

    if (!(employee.getRole() == BusinessRole.OWNER)) {
      throw new ForbiddenException("You are not allowed to delete this company");
    }

    employeeRepository.deactivateAllByCompanyIdExcept(id, ActiveStatus.INACTIVE, BusinessRole.OWNER);

    company.setStatus(ActiveStatus.INACTIVE);
    companyRepository.save(company);
  }

  private User getAuthenticatedUser() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }
}
