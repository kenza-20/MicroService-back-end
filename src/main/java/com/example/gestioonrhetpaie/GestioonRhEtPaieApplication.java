package com.example.gestioonrhetpaie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class GestioonRhEtPaieApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestioonRhEtPaieApplication.class, args);
		System.out.println("HELLO WORLD !!");
		System.out.println(LocalDate.now());
		//maram
	}

}
