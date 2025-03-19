package com.cases.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public Mono<String> fetchFilteredChroma3Skins() {
        return webClient.get()
                .uri("/CSGO-API/api/en/collections.json")
                .retrieve()
                .bodyToMono(String.class)
                .map(this::filterSkinsWithChroma3Case);
    }

    public Mono<String> obtainedChroma3Skins() {
        return webClient.get()
                .uri("/CSGO-API/api/en/collections.json")
                .retrieve()
                .bodyToMono(String.class)
                .map(this::OpenChroma3Case);
    }

    private String filterSkinsWithChroma3Case(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode filteredSkins = objectMapper.createArrayNode();

            for (JsonNode collection : rootNode) {
                if (!collection.has("crates")) continue;

                for (JsonNode crate : collection.get("crates")) {
                    if ("Chroma 3 Case".equals(crate.get("name").asText())) {
                        ((com.fasterxml.jackson.databind.node.ArrayNode) filteredSkins).add(collection);
                        break;
                    }
                }
            }
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(filteredSkins);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"Error processing JSON response.\"}";
        }
    }

    private String OpenChroma3Case(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode filteredSkins = objectMapper.createArrayNode();
            JsonNode obtainedSkins = objectMapper.createArrayNode();

            for (JsonNode collection : rootNode) {
                if (!collection.has("crates")) continue;

                for (JsonNode crate : collection.get("crates")) {
                    if ("Chroma 3 Case".equals(crate.get("name").asText())) {
                        ((com.fasterxml.jackson.databind.node.ArrayNode) filteredSkins).add(collection);
                        break;
                    }
                }
            }
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(filteredSkins);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"Error processing JSON response.\"}";
        }
    }
}
