package com.cases.demo;

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

    public void fetchCollections() {
        Mono<String> response = webClient.get()
                .uri("/CSGO-API/api/en/collections.json")
                .retrieve()
                .bodyToMono(String.class);

        // Filter the response
        response.map(this::filterSkinsWithChroma3Case)
                .subscribe(filteredJson -> System.out.println("Filtered JSON: " + filteredJson));
    }

    private String filterSkinsWithChroma3Case(String response) {
        try {
            // Convert the response to a JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            // Create a new JsonNode to store filtered skins
            JsonNode filteredSkins = objectMapper.createArrayNode();

            // Iterate over the collections
            for (JsonNode collection : rootNode) {
                if (!collection.has("crates")) {
                    continue; // Skip if "crates" is missing
                }

                // Log all crate names to see the available ones
                for (JsonNode crate : collection.get("crates")) {
                    System.out.println("Crate name: " + crate.get("name").asText());
                }

                // Check for "Chroma 3 Case" in crates (case-sensitive match)
                for (JsonNode crate : collection.get("crates")) {
                    if ("Chroma 3 Case".equals(crate.get("name").asText())) {
                        // Add skin to filtered list if "Chroma 3 Case" is found
                        ((com.fasterxml.jackson.databind.node.ArrayNode) filteredSkins).add(collection);
                        break; // Stop once we've found a match
                    }
                }
            }

            // Return filtered JSON as a string
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(filteredSkins);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing JSON response.";
        }
    }

}
