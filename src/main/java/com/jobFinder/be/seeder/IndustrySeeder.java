
package com.jobFinder.be.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.jobFinder.be.model.Industry;
import com.jobFinder.be.repository.IndustryRepository;

import lombok.RequiredArgsConstructor;

@Component
@Order(2)
@RequiredArgsConstructor
public class IndustrySeeder implements CommandLineRunner {

  private final IndustryRepository industryRepository;

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Starting industry seeding");

    if (industryRepository.count() == 0) {
      seedIndustries();
    } else {
      System.out.println("Industries already exist, skipping industry seeding");
    }
  }

  private void seedIndustries() {
    try {
      System.out.println("Creating initial industries...");

      String[] industryNames = {
          "Technology",
          "Finance",
          "Healthcare",
          "Education",
          "Manufacturing",
          "Retail",
          "Construction",
          "Transportation"
      };

      for (String name : industryNames) {
        Industry industry = Industry.builder()
            .name(name)
            .build();
        industryRepository.save(industry);
      }

      System.out.println("✅ Industry seeding completed successfully!");
    } catch (Exception e) {
      System.err.println("❌ Error during industry seeding: " + e.getMessage());
      e.printStackTrace();
      throw new RuntimeException("Failed to seed industries", e);
    }
  }
}
