package com.project.rooton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;  // JpaAuditing

@SpringBootApplication
@EnableJpaAuditing  // JpaAuditing
public class RootonApplication {

	public static void main(String[] args) {
		SpringApplication.run(RootonApplication.class, args);
	}
}