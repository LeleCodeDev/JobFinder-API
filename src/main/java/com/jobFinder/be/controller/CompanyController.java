package com.jobFinder.be.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobFinder.be.dto.WebResponse;
import com.jobFinder.be.dto.company.CompanyRequest;
import com.jobFinder.be.dto.company.CompanyResponse;
import com.jobFinder.be.service.CompanyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

  private final CompanyService companyService;

  @PostMapping
  public ResponseEntity<WebResponse<CompanyResponse>> createCompany(@RequestBody @Valid CompanyRequest request) {
    CompanyResponse company = companyService.create(request);
    WebResponse<CompanyResponse> response = WebResponse.success("Company successfully created", company);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<WebResponse<CompanyResponse>> updateCompany(@PathVariable Long id,
      @RequestBody @Valid CompanyRequest request) {
    CompanyResponse company = companyService.update(id, request);
    WebResponse<CompanyResponse> response = WebResponse.success("Company successfully updated", company);
    return ResponseEntity.ok(response);
  }

  @GetMapping()
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<WebResponse<List<CompanyResponse>>> getAllCompanies() {
    List<CompanyResponse> companies = companyService.getAll();
    WebResponse<List<CompanyResponse>> response = WebResponse.success("All companies successfully fetched",
        companies);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<WebResponse<CompanyResponse>> getById(@PathVariable Long id) {
    CompanyResponse company = companyService.getById(id);
    WebResponse<CompanyResponse> response = WebResponse.success("Company successfully fetched", company);
    return ResponseEntity.ok(response);
  }
}
