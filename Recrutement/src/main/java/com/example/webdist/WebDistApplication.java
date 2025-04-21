package com.example.webdist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WebDistApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebDistApplication.class, args);
    }

}
