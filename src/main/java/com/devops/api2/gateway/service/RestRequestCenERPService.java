package com.devops.api2.gateway.service;

import com.devops.api2.gateway.service.definition.Api2ErpDefinition;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
public class RestRequestCenERPService {

    private final WebClient webClient;
    private final Api2ErpDefinition api2ErpDefinition;

    public RestRequestCenERPService(WebClient.Builder webClientBuilder, @Value("${definition.baseUrl}") String baseUrl, Api2ErpDefinition api2ErpDefinition) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.api2ErpDefinition = api2ErpDefinition;
    }

    @CircuitBreaker(name = "erpServiceBaseInfoCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getBaseInfoData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2ErpDefinition.getBaseInfo());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class);
    }

    @CircuitBreaker(name = "erpServiceDeptCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getDeptData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2ErpDefinition.getDept());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class);
    }

    @CircuitBreaker(name = "erpServiceCompanyCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getCompanyData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2ErpDefinition.getDept());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class);
    }

    @CircuitBreaker(name = "erpServiceAcntinfoCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getAcntinfoData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2ErpDefinition.getAcntinfo());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class);
    }


    @CircuitBreaker(name = "erpServiceAcntsmapCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getAcntsmapData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2ErpDefinition.getAcntsmap());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class);
    }



    @CircuitBreaker(name = "erpServiceArcollectsCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getArcollectsData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2ErpDefinition.getArcollects());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class);
    }

    @CircuitBreaker(name = "erpServiceVendorbondsCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getVendorbondsData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2ErpDefinition.getVendorbonds());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class);
    }

    @CircuitBreaker(name = "erpServiceSlipinfosCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getSlipinfosData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2ErpDefinition.getSlipinfos());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class);
    }

    @CircuitBreaker(name = "erpServiceVendorsCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getVendorsData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2ErpDefinition.getVendors());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class);
    }

    @CircuitBreaker(name = "erpServiceVendorschargesCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getVendorschargesData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2ErpDefinition.getVendorscharges());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> fallbackERP(MultiValueMap<String, String> queryParams, Throwable t) {
        return Mono.just("Target Service CenERP Unavailable. Please try again later.");
    }
}

