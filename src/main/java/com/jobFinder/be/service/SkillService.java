package com.jobFinder.be.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jobFinder.be.dto.skill.SkillRequest;
import com.jobFinder.be.dto.skill.SkillResponse;
import com.jobFinder.be.enums.ActiveStatus;
import com.jobFinder.be.exception.ResourceAlreadyTakenException;
import com.jobFinder.be.exception.ResourceNotFoundException;
import com.jobFinder.be.mapper.SkillMapper;
import com.jobFinder.be.model.Skill;
import com.jobFinder.be.model.User;
import com.jobFinder.be.repository.SkillRepository;
import com.jobFinder.be.util.UserUtil;
import com.jobFinder.be.util.ValidationUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SkillService {

  private final SkillRepository skillRepository;
  private final SkillMapper skillMapper;
  private final ValidationUtil validationUtil;
  private final UserUtil userUtil;

  @Transactional
  public SkillResponse create(SkillRequest request) {
    validationUtil.validateOrThrow(request);

    skillRepository.findByName(request.getName()).ifPresent(s -> {
      throw new ResourceAlreadyTakenException("Skill already exists");
    });

    Skill skill = skillMapper.toEntity(request);

    Skill savedSkill = skillRepository.save(skill);

    return skillMapper.toResponse(savedSkill);
  }

  @Transactional
  public SkillResponse update(Long id, SkillRequest request) {
    validationUtil.validateOrThrow(request);

    Skill skill = skillRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Skill not found with ID: " + id));

    if (!skill.getName().equals(request.getName())) {
      skillRepository.findByName(request.getName()).ifPresent(s -> {
        throw new ResourceAlreadyTakenException("Skill already exists");
      });
    }

    skillMapper.updateEntity(skill, request);

    Skill updatedSkill = skillRepository.save(skill);

    return skillMapper.toResponse(updatedSkill);
  }

  @Transactional(readOnly = true)
  public List<SkillResponse> getAll() {
    User user = userUtil.getAuthenticatedUserOrNull();

    List<Skill> skills = user != null && userUtil.isAdmin(user)
        ? skillRepository.findAll()
        : skillRepository.findAllByStatus(ActiveStatus.ACTIVE);

    return skills.stream()
        .map(skillMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Transactional
  public SkillResponse deactivate(Long id) {
    Skill skill = skillRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Skill not found with ID: " + id));

    skill.setStatus(ActiveStatus.INACTIVE);
    Skill updatedSkill = skillRepository.save(skill);

    return skillMapper.toResponse(updatedSkill);
  }

  @Transactional
  public SkillResponse activate(Long id) {
    Skill skill = skillRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Skill not found with ID: " + id));

    skill.setStatus(ActiveStatus.ACTIVE);
    Skill updatedSkill = skillRepository.save(skill);

    return skillMapper.toResponse(updatedSkill);
  }
}
