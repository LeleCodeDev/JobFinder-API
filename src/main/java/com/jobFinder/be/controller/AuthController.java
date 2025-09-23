package com.jobFinder.be.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobFinder.be.dto.WebResponse;
import com.jobFinder.be.dto.auth.AuthResponse;
import com.jobFinder.be.dto.auth.LoginRequest;
import com.jobFinder.be.dto.auth.RegisterRequest;
import com.jobFinder.be.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<WebResponse<AuthResponse>> register(@RequestBody @Valid RegisterRequest request) {
    AuthResponse auth = authService.register(request);
    WebResponse<AuthResponse> response = WebResponse.success("Account successfully registered", auth);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/login")
  public ResponseEntity<WebResponse<AuthResponse>> login(@RequestBody @Valid LoginRequest request) {
    AuthResponse auth = authService.login(request);
    WebResponse<AuthResponse> response = WebResponse.success("Login successful", auth);
    return ResponseEntity.ok(response);
  }
}
