package com.jobFinder.be.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobFinder.be.dto.industry.IndustryRequest;
import com.jobFinder.be.dto.industry.IndustryResponse;
import com.jobFinder.be.enums.ActiveStatus;
import com.jobFinder.be.exception.ResourceAlreadyTakenException;
import com.jobFinder.be.exception.ResourceNotFoundException;
import com.jobFinder.be.mapper.IndustryMapper;
import com.jobFinder.be.model.Industry;
import com.jobFinder.be.model.User;
import com.jobFinder.be.repository.IndustryRepository;
import com.jobFinder.be.util.UserUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndustryService {

  private final IndustryRepository industryRepository;
  private final IndustryMapper industryMapper;
  private final UserUtil userUtil;

  @Transactional
  public IndustryResponse create(IndustryRequest request) {
    industryRepository.findByName(request.getName()).ifPresent(i -> {
      throw new ResourceAlreadyTakenException("Industry name already exist");
    });

    Industry industry = industryMapper.toEntity(request);

    Industry savedIndustry = industryRepository.save(industry);

    return industryMapper.toResponse(savedIndustry);
  }

  @Transactional(readOnly = true)
  public List<IndustryResponse> getAll() {
    User user = userUtil.getAuthenticatedUserOrNull();

    List<Industry> industries = user != null && userUtil.isAdmin(user)
        ? industryRepository.findAll()
        : industryRepository.findAllByStatus(ActiveStatus.ACTIVE);

    return industries.stream()
        .map(industryMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional
  public IndustryResponse update(Long id, IndustryRequest request) {
    Industry industry = industryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Industry not found with ID: " + id));

    if (!industry.getName().equals(request.getName())) {
      industryRepository.findByName(request.getName()).ifPresent(i -> {
        throw new ResourceAlreadyTakenException("Industry name already exist");
      });
    }

    industryMapper.updateEntity(industry, request);

    Industry updatedIndustry = industryRepository.save(industry);

    return industryMapper.toResponse(updatedIndustry);
  }

  @Transactional
  public IndustryResponse deactivate(Long id) {
    Industry industry = industryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Industry not found with ID: " + id));

    industry.setStatus(ActiveStatus.INACTIVE);
    Industry updatedIndustry = industryRepository.save(industry);

    return industryMapper.toResponse(updatedIndustry);
  }

  @Transactional
  public IndustryResponse activate(Long id) {
    Industry industry = industryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Industry not found with ID: " + id));

    industry.setStatus(ActiveStatus.ACTIVE);
    Industry updatedIndustry = industryRepository.save(industry);

    return industryMapper.toResponse(updatedIndustry);
  }

}
