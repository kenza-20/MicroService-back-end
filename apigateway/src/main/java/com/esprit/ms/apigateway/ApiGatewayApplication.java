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
                        .uri("lb://employe-service"))//port order=8080

                        .route("job-offers-service",r->r.path("/api/joboffers/**") //tous les path sous order
                        .uri("lb://job-offers-service"))//port order=8081

                .build();
    }
}
