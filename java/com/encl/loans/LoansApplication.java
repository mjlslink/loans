package com.encl.loans;

import com.encl.loans.dto.LoansContactInfoDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {LoansContactInfoDto.class})
@OpenAPIDefinition(
        info = @Info(title = "Loans REST Service API Documentation",
                description = "Microservice documentation",
                version = "v1",
                contact = @Contact(name = "Michael Larsen", email = "", url=""),
                license = @License(name="Aoache 2.0", url="")

        )

)
public class LoansApplication {

	public static void main(String[] args) {
        SpringApplication.run(LoansApplication.class, args);
	}

}
