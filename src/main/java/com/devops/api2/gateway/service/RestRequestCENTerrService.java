package com.devops.api2.gateway.service;

import com.devops.api2.gateway.service.definition.Api2CENTerrDefinition;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class RestRequestCENTerrService {

    private static final Logger log = LoggerFactory.getLogger(RestRequestCENTerrService.class);

    private final WebClient webClient;
    private final Api2CENTerrDefinition api2CENTerrDefinition;

    //10MB까지 허용, 반환하는 데이터의 Buffer size 가 이 옵션보다 작아야함!
    private final int byteCnt = 30720 * 1024;

    public RestRequestCENTerrService(WebClient.Builder webClientBuilder, @Value("${definition.baseUrl}") String baseUrl, Api2CENTerrDefinition api2CENTerrDefinition) {
        this.webClient = webClientBuilder
                         .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(byteCnt))
                         .baseUrl(baseUrl)
                         .build();
        this.api2CENTerrDefinition = api2CENTerrDefinition;
    }

//    @CircuitBreaker(name = "CENTerrServiceIFPMSERP01CircuitBreaker", fallbackMethod = "fallbackCENTerr" )
//    public Mono<String> getIFPMSERP01Data(MultiValueMap<String, String> queryParams) {
//        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(api2CENTerrDefinition.getIFPMSERP01());
//        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));
//
//        return this.webClient.get()
//                .uri(uriBuilder.build().toUriString())
//                .retrieve()
//                .bodyToMono(String.class);
//    }

    @CircuitBreaker(name = "CENTerrServiceIFPMSERP01CircuitBreaker", fallbackMethod = "fallbackCENTerr" )
    public Mono<String> getReversetaxesData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2CENTerrDefinition.getIFPMSERP01(), queryParams, jwtToken);
    }


    public Mono<String> fallbackCENTerr(Throwable t) {
        return Mono.just("Target Service CENTerr Unavailable. Please try again later.");
    }



    private Mono<String> fetchData(String apiPath, MultiValueMap<String, String> queryParams, String jwtToken) {
        UriComponentsBuilder uriBuilder = buildUri(apiPath, queryParams);
        Mono<String> responseMono = this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .header("Internal-Route-Request", "true")
                .header("Authorization", "Bearer " + jwtToken)
                .retrieve()
                .bodyToMono(String.class);
        return processResponse(responseMono);
    }


    /**
     * WebClient > HTTPRequest METHOD "POST"
     * @param apiPath
     * @param requestBody
     * @return
     */
    private Mono<String> fetchDataPost(String apiPath, Map<String, Object> requestBody, String jwtToken) {
        UriComponentsBuilder uriBuilder = buildUri(apiPath, null);
        Mono<String> responseMono = this.webClient.post()
                .uri(uriBuilder.build().toUriString())
                .header("Internal-Route-Request", "true")
                .header("Authorization", "Bearer " + jwtToken)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);
        return processResponse(responseMono);
    }

    /**
     * URI Path 빌드
     * @param apiPath
     * @param queryParams
     * @return
     */
    private UriComponentsBuilder buildUri(String apiPath, MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(apiPath);
        if (queryParams != null) {
            queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));
        }
        return uriBuilder;
    }

    /**
     * Data 반환
     * @param responseMono
     * @return
     */
    private Mono<String> processResponse(Mono<String> responseMono) {
        return responseMono.map(response -> {
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(Map.class, new GsonDeserializer())
                            .serializeNulls()
                            .setPrettyPrinting()
                            .create();
                    Map<String, Object> resultMap = gson.fromJson(response, new TypeToken<Map<String, Object>>() {}.getType());
                    return gson.toJson(resultMap);
                })
                .doOnNext(response -> {
                    String methodName = new Throwable().getStackTrace()[1].getMethodName();
                    log.debug("Method " + methodName + " - Successful response received!");
                })
                .doOnError(error -> {
                    String methodName = new Throwable().getStackTrace()[1].getMethodName();
                    log.error("Method " + methodName + " - Failed to receive response: " + error.getMessage());
                });
    }

    /**
     * 여기서 Route의 결과에 대한 exception처리는 여기서 하도록함.
     * @param queryParams
     * @param t
     * @return
     */
    public Mono<String> fallbackERP(MultiValueMap<String, String> queryParams, String jwtToken, Throwable t) {
        return Mono.just("Target Service CENTerr Unavailable. Please try again later.");
    }

    public Mono<String> fallbackPostERP(Map<String, Object> requestBody, Throwable t) {
        return Mono.just("Target Service CENTerr Unavailable. Please try again later.");
    }
}
