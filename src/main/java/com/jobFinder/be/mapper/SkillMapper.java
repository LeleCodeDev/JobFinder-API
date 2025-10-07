package com.jobFinder.be.mapper;

import org.springframework.stereotype.Component;

import com.jobFinder.be.dto.skill.SkillRequest;
import com.jobFinder.be.dto.skill.SkillResponse;
import com.jobFinder.be.enums.ActiveStatus;
import com.jobFinder.be.model.Skill;

@Component
public class SkillMapper {

  public SkillResponse toResponse(Skill skill) {
    return SkillResponse.builder()
        .id(skill.getId())
        .name(skill.getName())
        .status(skill.getStatus())
        .createdAt(skill.getCreatedAt())
        .updatedAt(skill.getUpdatedAt())
        .build();
  }

  public Skill toEntity(SkillRequest request) {
    return Skill.builder()
        .name(request.getName())
        .status(ActiveStatus.ACTIVE)
        .build();
  }

  public void updateEntity(Skill existingSkill, SkillRequest request) {
    existingSkill.setName(request.getName());
  }
}
