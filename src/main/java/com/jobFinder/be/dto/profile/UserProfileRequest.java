package com.jobFinder.be.dto.profile;

import java.time.LocalDate;

import com.jobFinder.be.model.UserProfile.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileRequest {

  @NotBlank(message = "Full name is required")
  private String fullName;

  @Past(message = "Date of birth cannot be in the future")
  @NotNull(message = "Date of birth is required")
  private LocalDate dateOfBirth;

  @NotBlank(message = "Gender number is required")
  private Gender gender;

  @NotBlank(message = "Phone number is required")
  private String phone;

  private String bio;

  private String picture;

  private String address;
}
