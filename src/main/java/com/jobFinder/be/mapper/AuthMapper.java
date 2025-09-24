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

  public AuthResponse toResponse(String token, User user) {
    return AuthResponse.builder()
        .id(user.getId())
        .token(token)
        .email(user.getEmail())
        .role(user.getRole().name())
        .build();
  }

  public User toEntity(RegisterRequest request) {
    return User.builder()
        .username(request.getUsername())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .phoneNumber(request.getPhoneNumber())
        .role(UserRole.USER)
        .status(ActiveStatus.ACTIVE)
        .build();
  }
}
