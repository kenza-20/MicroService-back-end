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
    public RouteLocator getawayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                //nom de l'app ds app.propreties
                .route("employe-service",r->r.path("/api/employes/**") //tous les path sous order
                        .uri("http://employe-service:8082"))//port order=8082

                        .route("job-offers-service",r->r.path("/api/joboffers/**") //tous les path sous order
                        .uri("http://job-offers-service:8081"))//port order=8081

                        .route("conge-service",r->r.path("/api/conge/**") //tous les path sous order
                        .uri("http://conge-service:8085"))//port order=8085

                        .route("GestionPaie",r->r.path("/api/paie/**") //tous les path sous order
                        .uri("http://gestion-paie:8083"))//port order=8083

                        .route("GestionNotification",r->r.path("/api/notification/**") //tous les path sous order
                        .uri("http://gestion-notification:8086"))//port order=8086

                .build();
    }
}
