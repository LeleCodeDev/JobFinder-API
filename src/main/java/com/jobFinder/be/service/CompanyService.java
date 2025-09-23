package com.jobFinder.be.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional
  public CompanyResponse create(CompanyRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    User owner = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    if (companyRepository.existsByOwner(owner)) {
      throw new ResourceAlreadyTakenException("User can only own one company");
    }

    owner.setRole(UserRole.EMPLOYER);
    userRepository.save(owner);

    Industry industry = industryRepository.findById(request.getIndustryId())
        .orElseThrow(() -> new ResourceNotFoundException("Industry not found with ID: " + request.getIndustryId()));

    Company company = companyMapper.toEntity(request, industry, owner);
    Company savedCompany = companyRepository.save(company);

    return companyMapper.toResponse(savedCompany, industry);
  }

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

    return companyMapper.toResponse(updatedCompany, updatedCompany.getIndustry());

  }

}
