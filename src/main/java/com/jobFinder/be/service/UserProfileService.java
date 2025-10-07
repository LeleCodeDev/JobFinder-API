package com.jobFinder.be.service;

import org.springframework.stereotype.Service;

import com.jobFinder.be.repository.UserProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileService {

  private final UserProfileRepository userProfileRepository;

}
