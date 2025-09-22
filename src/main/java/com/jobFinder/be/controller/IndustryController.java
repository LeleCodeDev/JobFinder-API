package com.jobFinder.be.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobFinder.be.dto.WebResponse;
import com.jobFinder.be.dto.industry.IndustryRequest;
import com.jobFinder.be.dto.industry.IndustryResponse;
import com.jobFinder.be.service.IndustryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/industries")
@RequiredArgsConstructor
public class IndustryController {

  private final IndustryService industryService;

  @GetMapping
  public ResponseEntity<WebResponse<List<IndustryResponse>>> getAllIndustries() {
    List<IndustryResponse> industries = industryService.getAll();
    WebResponse<List<IndustryResponse>> response = WebResponse.success("All industries successfully fetched",
        industries);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<WebResponse<IndustryResponse>> createIndustry(@RequestBody @Valid IndustryRequest request) {
    IndustryResponse industry = industryService.create(request);
    WebResponse<IndustryResponse> response = WebResponse.success("Industry created successfully", industry);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<WebResponse<IndustryResponse>> updateIndustry(@PathVariable Long id,
      @RequestBody @Valid IndustryRequest request) {

    IndustryResponse industry = industryService.update(id, request);
    WebResponse<IndustryResponse> response = WebResponse.success("Industry updated successfully", industry);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<WebResponse<Void>> deleteIndustry(@PathVariable Long id) {
    industryService.delete(id);
    WebResponse<Void> response = WebResponse.success("Industry deleted successfully");
    return ResponseEntity.ok(response);
  }

}
