package com.jobFinder.be.seeder;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jobFinder.be.enums.ActiveStatus;
import com.jobFinder.be.enums.UserRole;
import com.jobFinder.be.model.User;
import com.jobFinder.be.model.UserProfile;
import com.jobFinder.be.repository.UserRepository;
import com.jobFinder.be.repository.UserProfileRepository;

import lombok.RequiredArgsConstructor;

@Component
@Order(1)
@RequiredArgsConstructor
public class UserSeeder implements CommandLineRunner {

  private final UserRepository userRepository;
  private final UserProfileRepository userProfileRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Starting user seeding");

    if (userRepository.countByRole(UserRole.SUPERADMIN) == 0) {
      seedSuperAdmin();
    } else {
      System.out.println("Superadmin already exists, skipping");
    }

    if (userRepository.countByRole(UserRole.ADMIN) == 0) {
      seedAdmins();
    } else {
      System.out.println("Admins already exist, skipping");
    }

    if (userRepository.countByRole(UserRole.USER) == 0) {
      seedUsers();
    } else {
      System.out.println("Regular users already exist, skipping");
    }
  }

  private void seedSuperAdmin() {
    User superadmin = User.builder()
        .username("superadmin")
        .email("superadmin@email.xyz")
        .password(passwordEncoder.encode("SuperAdmin123!"))
        .phoneNumber("000000000")
        .role(UserRole.SUPERADMIN)
        .status(ActiveStatus.ACTIVE)
        .build();
    userRepository.save(superadmin);

    UserProfile profile = UserProfile.builder()
        .user(superadmin)
        .fullname("Super Admin")
        .gender(UserProfile.Gender.MALE)
        .dateOfBirth(LocalDate.of(1990, 1, 1))
        .address("Admin HQ")
        .bio("Superadmin account")
        .build();
    userProfileRepository.save(profile);

    System.out.println("");
    System.out.println("📧 SUPERADMIN: superadmin@email.xyz | Password: SuperAdmin123!");

    System.out.println("✅ Superadmin seeded successfully!");
    System.out.println("");
  }

  private void seedAdmins() {
    List<String[]> admins = List.of(
        new String[] { "admin1", "admin1@email.xyz", "Admin123!", "123456789", "Admin One" },
        new String[] { "admin2", "admin2@email.xyz", "Admin123!", "987654321", "Admin Two" });

    for (String[] a : admins) {
      User admin = User.builder()
          .username(a[0])
          .email(a[1])
          .password(passwordEncoder.encode(a[2]))
          .phoneNumber(a[3])
          .role(UserRole.ADMIN)
          .status(ActiveStatus.ACTIVE)
          .build();
      User savedAdmin = userRepository.save(admin);

      UserProfile profile = UserProfile.builder()
          .user(savedAdmin)
          .fullname(a[4])
          .gender(UserProfile.Gender.FEMALE)
          .dateOfBirth(LocalDate.of(1995, 6, 15))
          .address("Admin Office")
          .bio("Admin account")
          .build();
      userProfileRepository.save(profile);

      System.out.println("");
      System.out.println("📧 ADMIN: " + a[1] + " | Password: " + a[2]);
    }

    System.out.println("✅ Multiple admins seeded successfully!");
    System.out.println("");
  }

  private void seedUsers() {
    List<String[]> users = List.of(
        new String[] { "user1", "user1@email.xyz", "User123!", "111111111", "User One" },
        new String[] { "user2", "user2@email.xyz", "User123!", "222222222", "User Two" },
        new String[] { "user3", "user3@email.xyz", "User123!", "333333333", "User Three" });

    for (String[] u : users) {
      User user = User.builder()
          .username(u[0])
          .email(u[1])
          .password(passwordEncoder.encode(u[2]))
          .phoneNumber(u[3])
          .role(UserRole.USER)
          .status(ActiveStatus.ACTIVE)
          .build();
      User savedUser = userRepository.save(user);

      UserProfile profile = UserProfile.builder()
          .user(savedUser)
          .fullname(u[4])
          .gender(UserProfile.Gender.MALE)
          .dateOfBirth(LocalDate.of(2000, 3, 20))
          .address("User Street")
          .bio("Regular user account")
          .build();
      userProfileRepository.save(profile);

      System.out.println("");
      System.out.println("📧 USER: " + u[1] + " | Password: " + u[2]);
    }

    System.out.println("✅ Multiple users seeded successfully!");
    System.out.println("");
  }
}
