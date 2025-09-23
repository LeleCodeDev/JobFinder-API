package com.jobFinder.be.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jobFinder.be.enums.ActiveStatus;
import com.jobFinder.be.enums.UserRole;
import com.jobFinder.be.model.User;
import com.jobFinder.be.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@Order(1)
@RequiredArgsConstructor
public class UserSeeder implements CommandLineRunner {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Starting user seeding");

    if (userRepository.countByRole(UserRole.SUPERADMIN) == 0) {
      seedUser();
    } else {
      System.out.println("User already exist, skipping user seeding");
    }
  }

  private void seedUser() {
    try {
      System.out.println("Creating initial users...");

      User user = User.builder()
          .username("superadmin")
          .email("superadmin@email.xyz")
          .password(passwordEncoder.encode("SuperAdmin123!"))
          .phoneNumber("000000000")
          .role(UserRole.SUPERADMIN)
          .status(ActiveStatus.ACTIVE)
          .build();

      userRepository.save(user);

      System.out.println("✅ User seeding completed successfully!");
      System.out.println("🔐 Default Credentials:");
      System.out.println("📧 SUPERADMIN: superadmin@email.xyz | Password: SuperAdmin123!");
    } catch (Exception e) {
      System.err.println("❌ Error during user seeding: " + e.getMessage());
      e.printStackTrace();
      throw new RuntimeException("Failed to seed users", e);
    }
  }
}
