package com.jobFinder.be.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.jobFinder.be.dto.auth.AuthResponse;
import com.jobFinder.be.dto.auth.LoginRequest;
import com.jobFinder.be.dto.auth.RegisterRequest;
import com.jobFinder.be.exception.ResourceAlreadyTakenException;
import com.jobFinder.be.mapper.AuthMapper;
import com.jobFinder.be.model.AuthUserDetails;
import com.jobFinder.be.model.User;
import com.jobFinder.be.repository.UserRepository;
import com.jobFinder.be.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final AuthMapper authMapper;
  private final JwtUtil jwtUtil;

  public AuthResponse login(LoginRequest request) {
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    AuthUserDetails userDetails = (AuthUserDetails) authentication.getPrincipal();

    String role = userDetails.getUser().getRole().name();

    String token = jwtUtil.generateToken(userDetails, role);

    return authMapper.toResponse(token, userDetails.getUsername(), role);
  }

  public AuthResponse register(RegisterRequest request) {
    userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
      throw new ResourceAlreadyTakenException("Email already exist");
    });

    User user = authMapper.toEntity(request);

    User savedUser = userRepository.save(user);

    String role = savedUser.getRole().name();

    String token = jwtUtil.generateToken(new AuthUserDetails(savedUser), role);

    return authMapper.toResponse(token, savedUser.getEmail(), role);
  }
}
