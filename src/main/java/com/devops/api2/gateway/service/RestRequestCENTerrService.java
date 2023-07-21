package com.devops.api2.gateway.service;

import com.devops.api2.gateway.service.definition.Api2CENTerrDefinition;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
public class RestRequestCENTerrService {

    private final WebClient webClient;
    private final Api2CENTerrDefinition api2CENTerrDefinition;

    //10MB까지 허용, 반환하는 데이터의 Buffer size 가 이 옵션보다 작아야함!
    private final int byteCnt = 10240 * 1024;

    public RestRequestCENTerrService(WebClient.Builder webClientBuilder, @Value("${definition.baseUrl}") String baseUrl, Api2CENTerrDefinition api2CENTerrDefinition) {
        this.webClient = webClientBuilder
                         .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(byteCnt))
                         .baseUrl(baseUrl)
                         .build();
        this.api2CENTerrDefinition = api2CENTerrDefinition;
    }

    @CircuitBreaker(name = "CENTerrServiceIF-PMSERP-01CircuitBreaker", fallbackMethod = "fallbackCENTerr" )
    public Mono<String> getIFPMSERP01Data(MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2CENTerrDefinition.getIFPMSERP01());
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .retrieve()
                .bodyToMono(String.class);
    }


    public Mono<String> fallbackCENTerr(Throwable t) {
        return Mono.just("Target Service CENTerr Unavailable. Please try again later.");
    }
}
