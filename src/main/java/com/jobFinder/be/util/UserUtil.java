package com.jobFinder.be.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.jobFinder.be.enums.UserRole;
import com.jobFinder.be.exception.ResourceNotFoundException;
import com.jobFinder.be.model.User;
import com.jobFinder.be.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserUtil {

  private final UserRepository userRepository;

  public User getAuthenticatedUser() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  public User getAuthenticatedUserOrNull() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()
        || "anonymousUser".equals(authentication.getPrincipal())) {
      return null;
    }

    String email = authentication.getName();
    return userRepository.findByEmail(email).orElse(null);
  }

  public boolean isAdmin(User user) {
    return user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.SUPERADMIN;
  }
}
