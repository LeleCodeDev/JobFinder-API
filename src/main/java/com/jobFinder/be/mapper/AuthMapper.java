package com.jobFinder.be.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jobFinder.be.dto.auth.AuthResponse;
import com.jobFinder.be.dto.auth.RegisterRequest;
import com.jobFinder.be.enums.ActiveStatus;
import com.jobFinder.be.enums.UserRole;
import com.jobFinder.be.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthMapper {
  private final PasswordEncoder passwordEncoder;

  public AuthResponse toResponse(String token, String email, String role) {
    return AuthResponse.builder()
        .token(token)
        .email(email)
        .role(role)
        .build();
  }

  public User toEntity(RegisterRequest request) {
    return User.builder()
        .username(request.getUsername())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .phoneNumber(request.getPhoneNumber())
        .role(UserRole.JOBSEEKER)
        .status(ActiveStatus.ACTIVE)
        .build();
  }
}
