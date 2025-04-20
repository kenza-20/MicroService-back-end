package com.micro.backendmicro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@EnableDiscoveryClient
public class BackendmicroApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendmicroApplication.class, args);
	}

}
