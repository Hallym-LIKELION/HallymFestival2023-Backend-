package com.hallym.festival;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //AuditingEntityListener 활성화
public class FestivalApplication {
	public static void main(String[] args) {
		SpringApplication.run(FestivalApplication.class, args);
	}

}
