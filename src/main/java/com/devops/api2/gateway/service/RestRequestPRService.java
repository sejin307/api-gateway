package com.devops.api2.gateway.service;

import com.devops.api2.gateway.service.definition.Api2PRDefinition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class RestRequestPRService {

    private final WebClient webClient;
    private final Api2PRDefinition api2PRDefinition;
    //10MB까지 허용, 반환하는 데이터의 Buffer size 가 이 옵션보다 작아야함!
    private final int byteCnt = 10240 * 1024;

    public RestRequestPRService(WebClient.Builder webClientBuilder, @Value("${definition.baseUrl}") String baseUrl, Api2PRDefinition api2PRDefinition) {
        this.webClient = webClientBuilder
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(byteCnt))
                        .baseUrl(baseUrl)
                        .build();
        this.api2PRDefinition = api2PRDefinition;
    }

    public Mono<String> fallbackPR(){
        return Mono.just("Target Service Purchase Unavailable. Please try again later.");
    }
}
