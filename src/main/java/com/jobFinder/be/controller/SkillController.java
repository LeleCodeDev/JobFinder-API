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
import com.jobFinder.be.dto.skill.SkillRequest;
import com.jobFinder.be.dto.skill.SkillResponse;
import com.jobFinder.be.service.SkillService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/skills")
@RequiredArgsConstructor
public class SkillController {

  private final SkillService skillService;

  @GetMapping
  public ResponseEntity<WebResponse<List<SkillResponse>>> getAllSkills() {
    List<SkillResponse> skills = skillService.getAll();
    WebResponse<List<SkillResponse>> response = WebResponse.success("All skills successfully fetched", skills);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN')")
  public ResponseEntity<WebResponse<SkillResponse>> createSkill(@RequestBody SkillRequest request) {
    SkillResponse skill = skillService.create(request);
    WebResponse<SkillResponse> response = WebResponse.success("Skill successfully created", skill);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN')")
  public ResponseEntity<WebResponse<SkillResponse>> updatedSkill(@PathVariable Long id,
      @RequestBody SkillRequest request) {
    SkillResponse skill = skillService.update(id, request);
    WebResponse<SkillResponse> response = WebResponse.success("Skill successfully updated", skill);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}/deactivate")
  @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN')")
  public ResponseEntity<WebResponse<SkillResponse>> deactivateSkill(@PathVariable Long id) {
    SkillResponse skill = skillService.deactivate(id);
    WebResponse<SkillResponse> response = WebResponse.success("Skill successfully deactivated", skill);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}/activate")
  @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN')")
  public ResponseEntity<WebResponse<SkillResponse>> activateSkill(@PathVariable Long id) {
    SkillResponse skill = skillService.activate(id);
    WebResponse<SkillResponse> response = WebResponse.success("Skill successfully activated", skill);
    return ResponseEntity.ok(response);
  }
}
