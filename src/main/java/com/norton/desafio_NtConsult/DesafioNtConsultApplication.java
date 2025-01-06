package com.norton.desafio_NtConsult;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "VotingApi", version = "1", description = "API desenvolvida como desafio paara a NTConsult"))
public class DesafioNtConsultApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioNtConsultApplication.class, args);
	}

}
