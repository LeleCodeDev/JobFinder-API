package com.jobFinder.be.mapper;

import org.springframework.stereotype.Component;

import com.jobFinder.be.dto.company.CompanyFetchResponse;
import com.jobFinder.be.dto.company.CompanyRequest;
import com.jobFinder.be.dto.company.CompanyResponse;
import com.jobFinder.be.dto.user.OwnerResponse;
import com.jobFinder.be.enums.ActiveStatus;
import com.jobFinder.be.model.Company;
import com.jobFinder.be.model.Industry;
import com.jobFinder.be.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CompanyMapper {

  private final IndustryMapper industryMapper;

  public CompanyResponse toResponse(Company company) {
    return CompanyResponse.builder()
        .id(company.getId())
        .name(company.getName())
        .description(company.getDescription())
        .website(company.getWebsite())
        .logo(company.getLogo())
        .banner(company.getBanner())
        .location(company.getLocation())
        .phone(company.getPhone())
        .industry(industryMapper.toResponse(company.getIndustry()))
        .foundedDate(company.getFoundedDate())
        .verified(company.getVerified())
        .status(company.getStatus())
        .createdAt(company.getCreatedAt())
        .updatedAt(company.getUpdatedAt())
        .build();
  }

  public CompanyFetchResponse toFetchResponse(Company company) {
    return CompanyFetchResponse.builder()
        .id(company.getId())
        .owner(OwnerResponse.builder()
            .id(company.getOwner().getId())
            .username(company.getOwner().getUsername())
            .build())
        .name(company.getName())
        .description(company.getDescription())
        .website(company.getWebsite())
        .logo(company.getLogo())
        .banner(company.getBanner())
        .location(company.getLocation())
        .phone(company.getPhone())
        .industry(industryMapper.toResponse(company.getIndustry()))
        .foundedDate(company.getFoundedDate())
        .verified(company.getVerified())
        .status(company.getStatus())
        .createdAt(company.getCreatedAt())
        .updatedAt(company.getUpdatedAt())
        .build();
  }

  public Company toEntity(CompanyRequest request, Industry industry, User owner) {
    return Company.builder()
        .owner(owner)
        .name(request.getName())
        .description(request.getDescription())
        .website(request.getWebsite())
        .logo(request.getLogo())
        .banner(request.getBanner())
        .location(request.getLocation())
        .phone(request.getPhone())
        .industry(industry)
        .foundedDate(request.getFoundedDate())
        .verified(false)
        .status(ActiveStatus.ACTIVE)
        .build();
  }

  public void updateEntity(Company existingCompany, CompanyRequest request) {
    existingCompany.setName(request.getName());
    existingCompany.setDescription(request.getDescription());
    existingCompany.setWebsite(request.getWebsite());
    existingCompany.setLogo(request.getLogo());
    existingCompany.setBanner(request.getBanner());
    existingCompany.setLocation(request.getLocation());
    existingCompany.setPhone(request.getPhone());
    existingCompany.setFoundedDate(request.getFoundedDate());
  }
}
