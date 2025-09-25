package com.jobFinder.be.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@Order(0)
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

  private final UserSeeder userSeeder;
  private final IndustrySeeder industrySeeder;

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Starting database seeding...");

    userSeeder.run(args);

    industrySeeder.run(args);

    System.out.println("✅ Database seeding completed!");
  }
}
