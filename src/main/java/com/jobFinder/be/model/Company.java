package com.jobFinder.be.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.jobFinder.be.enums.ActiveStatus;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "companies")
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
  private User owner;

  @Column(nullable = false)
  private String name;

  private String description;

  private String website;

  private String logo;

  private String banner;

  private String location;

  private String phone;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "industry_id", referencedColumnName = "id")
  private Industry industry;

  @Column(name = "founded_date")
  private LocalDate foundedDate;

  private Boolean verified;

  @Enumerated(EnumType.STRING)
  private ActiveStatus status;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
