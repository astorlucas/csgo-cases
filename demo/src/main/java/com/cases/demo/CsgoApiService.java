package com.cases.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CsgoApiService {
    private final WebClient webClient;

    public CsgoApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
        .baseUrl("https://bymykel.github.io")
        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)) // 10MB
        .build();
    }

    public void fetchCollections() {
        Mono<String> response = webClient.get()
                .uri("/CSGO-API/api/en/collections.json?id=collection-set-community-3")
                .retrieve()
                .bodyToMono(String.class);

        response.subscribe(System.out::println); // Print JSON response
    }
}