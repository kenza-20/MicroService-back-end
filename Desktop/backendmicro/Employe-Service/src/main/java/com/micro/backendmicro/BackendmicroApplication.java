package com.micro.backendmicro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackendmicroApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendmicroApplication.class, args);
	}

}
