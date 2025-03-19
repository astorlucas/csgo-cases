package com.cases.demo.controller;

import com.cases.demo.service.CsgoApiService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class CsgoApiController {
    private final CsgoApiService csgoApiService;

    public CsgoApiController(CsgoApiService csgoApiService) {
        this.csgoApiService = csgoApiService;
    }

    @GetMapping("/chroma3skins")
    public Mono<String> getFilteredChroma3Skins() {
        return csgoApiService.fetchFilteredChroma3Skins();
    }

    @GetMapping("/open/chroma3skins")
    public Mono<String> openChroma3Skins() {
        return csgoApiService.obtainedChroma3Skins();
    }
}
