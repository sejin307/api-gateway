package com.devops.api2.gateway.service;

import com.devops.api2.gateway.service.definition.Api2Definition;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RestRequestService {

    private final WebClient webClient;
    private final Api2Definition api2Definition;

    public RestRequestService(WebClient.Builder webClientBuilder, @Value("${api.baseUrl}") String baseUrl, Api2Definition api2Definition) {
        this.api2Definition = api2Definition;
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @Value("${api.baseUrl}")
    private String baseUrl;

    @Value("${api.hydraPath}")
    private String hydraPath;

    @Value("${api.overloadPath}")
    private String overloadPath;

    @Value("${api.ultraPath}")
    private String ultraPath;

    @CircuitBreaker(name = "hydraCircuitBreaker", fallbackMethod = "fallbackHydra")
    public Mono<String> getHydra() {
        return this.webClient.get().uri(api2Definition.getHydraPath()).retrieve().bodyToMono(String.class);
    }

    @CircuitBreaker(name = "overloadCircuitBreaker", fallbackMethod = "fallbackOverload")
    public Mono<String> getOverload() {
        return this.webClient.get().uri(api2Definition.getOverloadPath()).retrieve().bodyToMono(String.class);
    }

    @CircuitBreaker(name = "ultraCircuitBreaker", fallbackMethod = "fallbackUltra")
    public Mono<String> getUltra() {
        return this.webClient.get().uri(api2Definition.getUltraPath()).retrieve().bodyToMono(String.class);
    }


    /**
     * Fallback
     * @param
     * @return
     */
    public Mono<String> fallbackHydra(Throwable t) {
        return Mono.just("Fallback for hydra");
    }

    public Mono<String> fallbackOverload(Throwable t) {
        return Mono.just("Fallback for overload");
    }

    public Mono<String> fallbackUltra(Throwable t) {
        return Mono.just("Fallback for ultra");
    }
}

