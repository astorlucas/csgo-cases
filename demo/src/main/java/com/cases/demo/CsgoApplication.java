package com.cases.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CsgoApplication implements CommandLineRunner {
    private final CsgoApiService apiService;

    public CsgoApplication(CsgoApiService apiService) {
        this.apiService = apiService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CsgoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        apiService.fetchCollections();
    }
}
