package com.it.vh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VhApplication {

	public static void main(String[] args) {
		SpringApplication.run(VhApplication.class, args);
	}

}
