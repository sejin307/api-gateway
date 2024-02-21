package com.devops.api2.gateway.service;

import com.devops.api2.gateway.service.definition.Api2ErpDefinition;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class RestRequestCenERPService {
    private static final Logger log = LoggerFactory.getLogger(RestRequestCenERPService.class);

    //30MB까지 허용, 반환하는 데이터의 Buffer size 가 이 옵션보다 작아야함!
    private final int byteCnt = 30720 * 1024;

    private final WebClient webClient;
    private final Api2ErpDefinition api2ErpDefinition;

    public RestRequestCenERPService(WebClient.Builder webClientBuilder, @Value("${definition.baseUrl}") String baseUrl, Api2ErpDefinition api2ErpDefinition) {
        this.webClient = webClientBuilder
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(byteCnt))
                        .baseUrl(baseUrl)
                        .build();
        this.api2ErpDefinition = api2ErpDefinition;
    }

    @CircuitBreaker(name = "erpServiceBaseInfoCircuitBreaker", fallbackMethod = "fallbackERP")
    public Mono<String> getBaseInfoData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getBaseInfo(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceDeptCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getDeptData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getDept(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceCompanyCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getCompanyData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getCompany(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceAcntinfoCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getAcntinfoData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getAcntinfo(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceAcntsmapCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getAcntsmapData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getAcntsmap(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceArcollectsCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getArcollectsData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getArcollects(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceVendorbondsCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getVendorbondsData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getVendorbonds(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceSlipinfosCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getSlipinfosData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getSlipinfos(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceVendorsCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getVendorsData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getVendors(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceVendorschargesCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getVendorschargesData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getVendorscharges(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceMagaminfoCenerpCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getMagaminfoCenerpData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getMagaminfocenerp(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceMagamVendorBondsCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getMagamVendorBondsData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getMagamvendorbonds(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceorderDeptMoveCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getOrderdeptmoveData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getOrderdeptmove(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceDirctPersExpCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getDirctPersExpData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getDirctpersexp(), queryParams, jwtToken);
    }
    @CircuitBreaker(name = "erpServiceDeptAcntInfoCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getDeptAcntInfoData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getDeptacntinfo(), queryParams, jwtToken);
    }
    @CircuitBreaker(name = "erpServiceMagamInfoFinalCenerpCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getMagamInfoFinalCenerpData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getMagaminfofinalcenerp(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceProjectamtinfoCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getProjectamtinfoData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getProjectamtinfo(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceMagaminfocenpcsCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getMagaminfocenpcsData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getMagaminfocenpcs(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceTaxesCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getTaxesData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getTaxes(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceMagamplbondsCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getMagamplbondsData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getMagamplbonds(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceHometaxstatusCircuitBreaker", fallbackMethod = "fallbackPostERP" )
    public Mono<String> getHometaxstatusData(Map<String, Object> requestBody, String jwtToken) {
        return fetchDataPost(api2ErpDefinition.getHometaxstatus(), requestBody, jwtToken);
    }


    @CircuitBreaker(name = "erpServiceCostprojectinfoCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getCostProjectInfoData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getCostprojectinfo(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceReversetaxesCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getReversetaxesData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getReversetaxes(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceDeptpersexpCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getDeptpersexpData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getDeptpersexp(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceExchrateinfoCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getExchrateinfoData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getExchrateinfo(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceSlipinfosPostCircuitBreaker", fallbackMethod = "fallbackPostERP" )
    public Mono<String> getSlipinfosPostData(Map<String, Object> requestBody, String jwtToken) {
        return fetchDataPost(api2ErpDefinition.getSlipinfospost(), requestBody, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceReverseTaxesPostCircuitBreaker", fallbackMethod = "fallbackPostERP" )
    public Mono<String> getReverseTaxesPostData(Map<String, Object> requestBody, String jwtToken) {
        return fetchDataPost(api2ErpDefinition.getReversetaxespost(), requestBody, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceContUsersDoCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getContUsersDoData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getContusersdo(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceDeptSecucenCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getDeptSecucenData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getDeptsecucen(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceBaseinfoSecucenCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getBaseinfoSecucenData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getBaseinfosecucen(), queryParams, jwtToken);
    }

    @CircuitBreaker(name = "erpServiceOrderMMCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getOrderMMData(MultiValueMap<String, String> queryParams, String jwtToken) {
        return fetchData(api2ErpDefinition.getOrdermm(), queryParams, jwtToken);
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
        return Mono.just("Target Service CenERP Unavailable. Please try again later.");
    }

    public Mono<String> fallbackPostERP(Map<String, Object> requestBody,String jwtToken, Throwable t) {
        return Mono.just("Target Service CenERP Unavailable. Please try again later.");
    }
}

