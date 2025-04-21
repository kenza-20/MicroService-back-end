package com.esprit.ms.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator getawayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("employe-service", r -> r.path("/api/employes/**")
                        .uri("http://employe-service:8082"))

                .route("conge-service", r -> r.path("/api/conge/**")
                        .uri("http://conge-service:8085"))

                .route("GestionPaie", r -> r.path("/api/paie/**")
                        .uri("http://gestion-paie:8083"))

                .route("GestionNotification", r -> r.path("/api/notification/**")
                        .uri("http://gestion-notification:8086"))

                .route("auth-service", r -> r.path("/api/auth/**")
                        .uri("http://auth-app:3000")) // <-- ici tu utilises auth-app !

                .build();
    }
}
