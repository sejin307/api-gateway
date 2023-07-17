package com.devops.api2.gateway.service;

import com.devops.api2.gateway.service.definition.Api2Definition;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
public class RestRequestService {

    private final WebClient webClient;
    private final Api2Definition api2Definition;

    public RestRequestService(WebClient.Builder webClientBuilder, @Value("${api.baseUrl}") String baseUrl, Api2Definition api2Definition) {
        this.api2Definition = api2Definition;
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @CircuitBreaker(name = "erpServiceCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getBaseInfoData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2Definition.getErpPath());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class);
    }

    @CircuitBreaker(name = "centerrServiceCircuitBreaker", fallbackMethod = "fallbackCENTerr")
    public Mono<String> getCENTerrData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2Definition.getCENTerrPath());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class);
    }

    @CircuitBreaker(name = "prServiceCircuitBreaker", fallbackMethod = "fallbackUltra")
    public Mono<String> getPrData(MultiValueMap<String, String > queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2Definition.getPrPath());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> fallbackERP(MultiValueMap<String, String> queryParams, Throwable t) {
        return Mono.just("Target Service CenERP Unavailable. Please try again later.");
    }

    public Mono<String> fallbackOverload(Throwable t) {
        return Mono.just("Target Service CENTerr Unavailable. Please try again later.");
    }

    public Mono<String> fallbackUltra(Throwable t) {
        return Mono.just("Target Service Purchase Unavailable. Please try again later.");
    }
}

