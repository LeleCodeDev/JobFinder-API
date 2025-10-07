package com.jobFinder.be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobFinder.be.enums.ActiveStatus;
import com.jobFinder.be.model.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {

  Optional<Skill> findByName(String name);

  List<Skill> findAllByStatus(ActiveStatus status);
}
