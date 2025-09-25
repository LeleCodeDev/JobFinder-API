package com.jobFinder.be.mapper;

import org.springframework.stereotype.Component;

import com.jobFinder.be.dto.industry.IndustryRequest;
import com.jobFinder.be.dto.industry.IndustryResponse;
import com.jobFinder.be.enums.ActiveStatus;
import com.jobFinder.be.model.Industry;

@Component
public class IndustryMapper {

  public IndustryResponse toResponse(Industry industry) {
    return IndustryResponse.builder()
        .id(industry.getId())
        .name(industry.getName())
        .status(industry.getStatus())
        .createdAt(industry.getCreatedAt())
        .updatedAt(industry.getUpdatedAt())
        .build();
  }

  public Industry toEntity(IndustryRequest request) {
    return Industry.builder()
        .name(request.getName())
        .status(ActiveStatus.ACTIVE)
        .build();
  }

  public void updateEntity(Industry existingIndustry, IndustryRequest request) {
    existingIndustry.setName(request.getName());
  }

}
