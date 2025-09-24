package com.jobFinder.be.dto.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jobFinder.be.enums.ActiveStatus;
import com.jobFinder.be.enums.UserRole;
import com.jobFinder.be.model.UserProfile.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponse {

  private Long id;

  private String username;

  private String email;

  private String phoneNumber;

  private UserRole role;

  private ActiveStatus status;

  private String fullname;

  private LocalDate dateOfBirth;

  private Gender gender;

  private String address;

  private String bio;

  private String picture;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updatedAt;
}
