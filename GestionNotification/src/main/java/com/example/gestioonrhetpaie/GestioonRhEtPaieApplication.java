package com.example.gestioonrhetpaie;

import com.example.gestioonrhetpaie.Entity.Notification;
import com.example.gestioonrhetpaie.Repository.INotificationRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
public class GestioonRhEtPaieApplication {

	private final INotificationRepository notificationRepository;

	public GestioonRhEtPaieApplication(INotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(GestioonRhEtPaieApplication.class, args);
	}

	@Bean
	ApplicationRunner init() {
		return args -> {
			if (notificationRepository.count() == 0) {
				notificationRepository.saveAll(List.of(
						new Notification("employee1@example.com", "12345678", "Bienvenue !", LocalDateTime.now()),
						new Notification("employee2@example.com", "87654321", "Votre paie est prête.", LocalDateTime.now()),
						new Notification("employee3@example.com", "11223344", "Document RH disponible.", LocalDateTime.now())
				));
			}

			notificationRepository.findAll().forEach(System.out::println);
		};
	}
}
