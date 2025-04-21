package com.example.gestioonrhetpaie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.time.LocalDate;

@SpringBootApplication
@EnableDiscoveryClient
public class GestioonRhEtPaieApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestioonRhEtPaieApplication.class, args);
		System.out.println("HELLO WORLD !!");
		System.out.println(LocalDate.now());
		//maram
	}

}
