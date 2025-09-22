package com.jobFinder.be.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jobFinder.be.dto.industry.IndustryRequest;
import com.jobFinder.be.dto.industry.IndustryResponse;
import com.jobFinder.be.exception.ResourceAlreadyTakenException;
import com.jobFinder.be.exception.ResourceNotFoundException;
import com.jobFinder.be.mapper.IndustryMapper;
import com.jobFinder.be.model.Industry;
import com.jobFinder.be.repository.IndustryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndustryService {

  private final IndustryRepository industryRepository;
  private final IndustryMapper industryMapper;

  public IndustryResponse create(IndustryRequest request) {
    industryRepository.findByName(request.getName()).ifPresent(i -> {
      throw new ResourceAlreadyTakenException("Industry name already exist");
    });

    Industry industry = industryMapper.toEntity(request);

    Industry savedIndustry = industryRepository.save(industry);

    return industryMapper.toResponse(savedIndustry);
  }

  public List<IndustryResponse> getAll() {
    List<Industry> industries = industryRepository.findAll();

    return industries.stream()
        .map(industryMapper::toResponse)
        .collect(Collectors.toList());
  }

  public IndustryResponse update(Long id, IndustryRequest request) {
    Industry industry = industryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Industry not found with ID: " + id));

    industryMapper.updateEntity(industry, request);

    Industry updatedIndustry = industryRepository.save(industry);

    return industryMapper.toResponse(updatedIndustry);
  }

  public void delete(Long id) {
    Industry industry = industryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Industry not found with ID: " + id));

    industryRepository.delete(industry);
  }

}
