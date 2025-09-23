package com.jobFinder.be.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobFinder.be.dto.company.CompanyFetchResponse;
import com.jobFinder.be.dto.company.CompanyRequest;
import com.jobFinder.be.dto.company.CompanyResponse;
import com.jobFinder.be.enums.UserRole;
import com.jobFinder.be.exception.ForbiddenException;
import com.jobFinder.be.exception.ResourceAlreadyTakenException;
import com.jobFinder.be.exception.ResourceNotFoundException;
import com.jobFinder.be.mapper.CompanyMapper;
import com.jobFinder.be.model.Company;
import com.jobFinder.be.model.Industry;
import com.jobFinder.be.model.User;
import com.jobFinder.be.repository.CompanyRepository;
import com.jobFinder.be.repository.IndustryRepository;
import com.jobFinder.be.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {

  private final CompanyRepository companyRepository;
  private final UserRepository userRepository;
  private final IndustryRepository industryRepository;
  private final CompanyMapper companyMapper;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public CompanyResponse create(CompanyRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    User owner = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    if (companyRepository.existsByOwner(owner)) {
      throw new ResourceAlreadyTakenException("User can only own one company");
    }

    if (owner.getRole().equals(UserRole.JOBSEEKER)) {
      owner.setRole(UserRole.EMPLOYER);
      userRepository.save(owner);
    }

    Industry industry = industryRepository.findById(request.getIndustryId())
        .orElseThrow(() -> new ResourceNotFoundException("Industry not found with ID: " + request.getIndustryId()));

    Company company = companyMapper.toEntity(request, industry, owner);
    Company savedCompany = companyRepository.save(company);

    return companyMapper.toResponse(savedCompany);
  }

  @Transactional
  public CompanyResponse update(Long id, CompanyRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    Company company = companyRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + id));

    if (!company.getOwner().getId().equals(user.getId())) {
      throw new ForbiddenException("You are not the owner of this company");
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
  public CompanyResponse getDashboard() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    User owner = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    Company company = companyRepository.findByOwnerId(owner.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Company not found with owner: " + owner.getId()));

    return companyMapper.toResponse(company);
  }

  @Transactional(readOnly = true)
  public List<CompanyFetchResponse> getAll() {
    List<Company> companies = companyRepository.findAll();

    return companies.stream()
        .map(companyMapper::toFetchResponse)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public CompanyFetchResponse getById(Long id) {
    Company company = companyRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + id));

    return companyMapper.toFetchResponse(company);
  }

  @Transactional
  public void delete(Long id, String password) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    User owner = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    if (!passwordEncoder.matches(password, owner.getPassword())) {
      throw new ForbiddenException("Invalid password");
    }

    Company company = companyRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + id));

    if (!company.getOwner().getId().equals(owner.getId())) {
      throw new ForbiddenException("You are not the owner of this company");
    }

    companyRepository.delete(company);
  }
}
