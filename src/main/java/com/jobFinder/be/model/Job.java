package com.jobFinder.be.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jobs")
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Job {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id", referencedColumnName = "id")
  private Company company;

  private String title;

  private String description;

  private String location;

  private String currency;

  @Column(name = "salary_min")
  private Integer salaryMin;

  @Column(name = "salary_max")
  private Integer salaryMax;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  private JobCategory category;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private JobStatus status;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false)
  private JobType type;

  @Column(name = "applications_count", nullable = false)
  private Integer applicationsCount;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public enum JobStatus {
    OPEN,
    CLOSE,
    BANNED
  }

  public enum JobType {
    FULL_TIME,
    PART_TIME,
    INTERNSHIP,
    CONTRACT,
    FREELANCE,
    TEMPORARY
  }
}
