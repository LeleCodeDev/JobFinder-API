package com.jobFinder.be.mapper;

import org.springframework.stereotype.Component;

import com.jobFinder.be.dto.user.UserResponse;
import com.jobFinder.be.model.UserProfile;

@Component
public class UserProfileMapper {

  public UserResponse toResponse(UserProfile userProfile) {
    return UserResponse.builder()
        .username(userProfile.getUser().getUsername())
        .email(userProfile.getUser().getEmail())
        .phoneNumber(userProfile.getUser().getPhoneNumber())
        .role(userProfile.getUser().getRole())
        .status(userProfile.getUser().getStatus())
        .fullname(userProfile.getFullname())
        .dateOfBirth(userProfile.getDateOfBirth())
        .gender(userProfile.getGender())
        .address(userProfile.getAddress())
        .bio(userProfile.getBio())
        .picture(userProfile.getPicture())
        .createdAt(userProfile.getCreatedAt())
        .updatedAt(userProfile.getUpdatedAt())
        .build();
  }

}
