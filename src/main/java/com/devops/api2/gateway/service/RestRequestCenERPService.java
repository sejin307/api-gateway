package com.devops.api2.gateway.service;

import com.devops.api2.gateway.service.definition.Api2ErpDefinition;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.security.cert.CertPathBuilder;

@Service
public class RestRequestCenERPService {

    private final WebClient webClient;
    private final Api2ErpDefinition api2ErpDefinition;

    public RestRequestCenERPService(WebClient.Builder webClientBuilder, @Value("${definition.baseUrl}") String baseUrl, Api2ErpDefinition api2ErpDefinition) {
        this.webClient = webClientBuilder
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10240 * 1024))//10MB까지 허용, 반환하는 데이터의 Buffer size 가 이 옵션보다 작아야함!
                        .baseUrl(baseUrl)
                        .build();
        this.api2ErpDefinition = api2ErpDefinition;
    }

    @CircuitBreaker(name = "erpServiceBaseInfoCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getBaseInfoData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2ErpDefinition.getBaseInfo());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Received response: " + response));
    }

    @CircuitBreaker(name = "erpServiceDeptCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getDeptData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2ErpDefinition.getDept());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Received response: " + response));
    }

    @CircuitBreaker(name = "erpServiceCompanyCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getCompanyData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2ErpDefinition.getCompany());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Received response: " + response));
    }

    @CircuitBreaker(name = "erpServiceAcntinfoCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getAcntinfoData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2ErpDefinition.getAcntinfo());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Received response: " + response));
    }


    @CircuitBreaker(name = "erpServiceAcntsmapCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getAcntsmapData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2ErpDefinition.getAcntsmap());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Received response: " + response));
    }



    @CircuitBreaker(name = "erpServiceArcollectsCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getArcollectsData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2ErpDefinition.getArcollects());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Received response: " + response));
    }

    @CircuitBreaker(name = "erpServiceVendorbondsCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getVendorbondsData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2ErpDefinition.getVendorbonds());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Received response: " + response));
    }

    @CircuitBreaker(name = "erpServiceSlipinfosCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getSlipinfosData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2ErpDefinition.getSlipinfos());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Received response: " + response));
    }

    @CircuitBreaker(name = "erpServiceVendorsCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getVendorsData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2ErpDefinition.getVendors());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Received response: " + response));
    }

    @CircuitBreaker(name = "erpServiceVendorschargesCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getVendorschargesData(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2ErpDefinition.getVendorscharges());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Received response: " + response));
    }

    public Mono<String> fallbackERP(MultiValueMap<String, String> queryParams, Throwable t) {
        return Mono.just("Target Service CenERP Unavailable. Please try again later.");
    }
}

