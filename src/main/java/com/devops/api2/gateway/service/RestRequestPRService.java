package com.devops.api2.gateway.service;

import com.devops.api2.gateway.service.definition.Api2PRDefinition;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class RestRequestPRService {

    private final WebClient webClient;
    private final Api2PRDefinition api2PRDefinition;

    public RestRequestPRService(WebClient webClient, Api2PRDefinition api2PRDefinition) {
        this.webClient = webClient;
        this.api2PRDefinition = api2PRDefinition;
    }

    public Mono<String> fallbackPR(){
        return Mono.just("Target Service Purchase Unavailable. Please try again later.");
    }
}
