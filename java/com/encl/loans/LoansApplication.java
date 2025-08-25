package com.encl.loans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {com.encl.loans.dto.LoansContactInfoDto.class})
public class LoansApplication {

	public static void main(String[] args) {
        SpringApplication.run(LoansApplication.class, args);
	}

}
