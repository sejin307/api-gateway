package com.devops.api2.gateway.service;

import com.devops.api2.gateway.service.definition.Api2PRDefinition;
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
public class RestRequestPRService {

    private static final Logger log = LoggerFactory.getLogger(RestRequestPRService.class);

    //30MB까지 허용, 반환하는 데이터의 Buffer size 가 이 옵션보다 작아야함!
    private final int byteCnt = 30720 * 1024;

    private final WebClient webClient;
    private final Api2PRDefinition api2PRDefinition;

    public RestRequestPRService(WebClient.Builder webClientBuilder, @Value("${definition.baseUrl}") String baseUrl, Api2PRDefinition api2PRDefinition) {
        this.webClient = webClientBuilder
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(byteCnt))
                        .baseUrl(baseUrl)
                        .build();
        this.api2PRDefinition = api2PRDefinition;
    }

    @CircuitBreaker(name = "prServiceVendorBuyerInfoCircuitBreaker", fallbackMethod = "fallbackPR")
    public Mono<String> getVendorBuyerInfoData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2PRDefinition.getGetVendorBuyerInfo(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "prServiceVendorNomalInfoCircuitBreaker", fallbackMethod = "fallbackPR" )
    public Mono<String> getVendorNomalInfoData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2PRDefinition.getGetVendorNomalInfo(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "prServiceVendorManagerInfoCircuitBreaker", fallbackMethod = "fallbackPR" )
    public Mono<String> getVendorManagerInfoData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2PRDefinition.getGetVendorManagerInfo(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "prServiceContractInfoCircuitBreaker", fallbackMethod = "fallbackPR" )
    public Mono<String> getContractInfoData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2PRDefinition.getGetContractInfo(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "prServiceContractMonthlyPayInfoCircuitBreaker", fallbackMethod = "fallbackPR" )
    public Mono<String> getContractMonthlyPayInfoData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2PRDefinition.getGetContractMonthlyPayInfo(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "prServiceFIPurchaseCostCircuitBreaker", fallbackMethod = "fallbackPR" )
    public Mono<String> getFIPurchaseCostData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2PRDefinition.getGetFIPurchaseCost(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "prServiceBusinessIncomePayConfirmInfoCircuitBreaker", fallbackMethod = "fallbackPR" )
    public Mono<String> getBusinessIncomePayConfirmInfoData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2PRDefinition.getGetBusinessIncomePayConfirmInfo(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "prServiceDoBusinessIncomePayConfirmCircuitBreaker", fallbackMethod = "fallbackPostPR" )
    public Mono<String> getDoBusinessIncomePayConfirmData(Map<String, Object> requestBody, String jwtToken) {
        return fetchDataPost(api2PRDefinition.getDoBusinessIncomePayConfirm(), requestBody, jwtToken);
    }

    @CircuitBreaker(name = "prServiceProjectSaveCircuitBreaker", fallbackMethod = "fallbackPostPR" )
    public Mono<String> projectSaveData(Map<String, Object> requestBody, String jwtToken) {
        return fetchDataPost(api2PRDefinition.getProjectSave(), requestBody, jwtToken);
    }

    @CircuitBreaker(name = "prServiceGetItemTaxonomyInfoCircuitBreaker", fallbackMethod = "fallbackPR" )
    public Mono<String> getItemTaxonomyInfoData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2PRDefinition.getGetItemTaxonomyInfo(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "prServiceGetItemStandardInfoCircuitBreaker", fallbackMethod = "fallbackPR" )
    public Mono<String> getItemStandardInfoData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2PRDefinition.getGetItemStandardInfo(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "prServiceCpResultCircuitBreaker", fallbackMethod = "fallbackPostPR" )
    public Mono<String> cpResultData(Map<String, Object> requestBody, String jwtToken) {
        return fetchDataPost(api2PRDefinition.getCpResult(), requestBody, jwtToken);
    }

    @CircuitBreaker(name = "prServiceGiResultCircuitBreaker", fallbackMethod = "fallbackPostPR" )
    public Mono<String> giResultData(Map<String, Object> requestBody, String jwtToken) {
        return fetchDataPost(api2PRDefinition.getGiResult(), requestBody, jwtToken);
    }

    @CircuitBreaker(name = "prServiceGetContractInfoCENTerrCircuitBreaker", fallbackMethod = "fallbackPR" )
    public Mono<String> getContractInfoCENTerr(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2PRDefinition.getGetContractInfoCENTerr(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "prServiceGetContractMonthlyPayPlanCircuitBreaker", fallbackMethod = "fallbackPR" )
    public Mono<String> getContractMonthlyPayPlan(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2PRDefinition.getGetContractMonthlyPayPlan(), queryParams, jwtToken);
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
    public Mono<String> fallbackPR(MultiValueMap<String, String> queryParams, String jwtToken, Throwable t) {
        return Mono.just("Target Service PR Unavailable. Please try again later.");
    }

    public Mono<String> fallbackPostPR(Map<String, Object> requestBody,String jwtToken, Throwable t) {
        return Mono.just("Target Service PR Unavailable. Please try again later.");
    }
}
