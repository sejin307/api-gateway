package com.devops.api2.gateway.service;

import com.devops.api2.gateway.service.definition.Api2ErpDefinition;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(RestRequestCenERPService.class);

    //10MB까지 허용, 반환하는 데이터의 Buffer size 가 이 옵션보다 작아야함!
    private final int byteCnt = 10240 * 1024;

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
    public Mono<String> getBaseInfoData(MultiValueMap<String, String> queryParams) {
        return fetchData(api2ErpDefinition.getBaseInfo(), queryParams);
    }

    @CircuitBreaker(name = "erpServiceDeptCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getDeptData(MultiValueMap<String, String> queryParams) {
        return fetchData(api2ErpDefinition.getDept(), queryParams);
    }

    @CircuitBreaker(name = "erpServiceCompanyCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getCompanyData(MultiValueMap<String, String> queryParams) {
        return fetchData(api2ErpDefinition.getCompany(), queryParams);
    }

    @CircuitBreaker(name = "erpServiceAcntinfoCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getAcntinfoData(MultiValueMap<String, String> queryParams) {
        return fetchData(api2ErpDefinition.getAcntinfo(), queryParams);
    }

    @CircuitBreaker(name = "erpServiceAcntsmapCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getAcntsmapData(MultiValueMap<String, String> queryParams) {
        return fetchData(api2ErpDefinition.getAcntsmap(), queryParams);
    }

    @CircuitBreaker(name = "erpServiceArcollectsCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getArcollectsData(MultiValueMap<String, String> queryParams) {
        return fetchData(api2ErpDefinition.getArcollects(), queryParams);
    }

    @CircuitBreaker(name = "erpServiceVendorbondsCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getVendorbondsData(MultiValueMap<String, String> queryParams) {
        return fetchData(api2ErpDefinition.getVendorbonds(), queryParams);
    }

    @CircuitBreaker(name = "erpServiceSlipinfosCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getSlipinfosData(MultiValueMap<String, String> queryParams) {
        return fetchData(api2ErpDefinition.getSlipinfos(), queryParams);
    }

    @CircuitBreaker(name = "erpServiceVendorsCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getVendorsData(MultiValueMap<String, String> queryParams) {
        return fetchData(api2ErpDefinition.getVendors(), queryParams);
    }

    @CircuitBreaker(name = "erpServiceVendorschargesCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getVendorschargesData(MultiValueMap<String, String> queryParams) {
        return fetchData(api2ErpDefinition.getVendorscharges(), queryParams);
    }

    @CircuitBreaker(name = "erpServiceMagaminfoCenerpCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getMagaminfoCenerpData(MultiValueMap<String, String> queryParams) {
        return fetchData(api2ErpDefinition.getMagaminfocenerp(), queryParams);
    }

    @CircuitBreaker(name = "erpServiceMagamVendorBondsCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getMagamVendorBondsData(MultiValueMap<String, String> queryParams) {
        return fetchData(api2ErpDefinition.getMagamvendorbonds(), queryParams);
    }

    @CircuitBreaker(name = "erpServiceorderDeptMoveCircuitBreaker", fallbackMethod = "fallbackERP" )
    public Mono<String> getOrderdeptmoveData(MultiValueMap<String, String> queryParams) {
        return fetchData(api2ErpDefinition.getOrderdeptmove(), queryParams);
    }



    private Mono<String> fetchData(String apiPath, MultiValueMap<String, String> queryParams) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(apiPath);
        queryParams.forEach((key, values) -> values.forEach(value -> uriBuilder.queryParam(key, value)));

        return this.webClient.get()
                .uri(uriBuilder.build().toUriString())
                .header("Internal-Route-Request","true")
                .retrieve()
                .bodyToMono(String.class)
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
    public Mono<String> fallbackERP(MultiValueMap<String, String> queryParams, Throwable t) {
        return Mono.just("Target Service CenERP Unavailable. Please try again later.");
    }
}

