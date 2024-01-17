package com.devops.api2.gateway.rest;


import com.devops.api2.gateway.service.RestRequestCenERPService;
import com.devops.api2.gateway.service.RestRequestPRService;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api2")
public class Api2PRController {


    private final RestRequestPRService restRequestPRService;

    public Api2PRController(RestRequestPRService restRequestPRService) {
        this.restRequestPRService = restRequestPRService;
    }

    @GetMapping("/interface/getVendorBuyerInfo-so")
    public Mono<String> getVendorBuyerInfoData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestPRService::getVendorBuyerInfoData,queryParams);
    }

    @GetMapping("/interface/getVendorNomalInfo-so")
    public Mono<String> getVendorNomalInfoData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestPRService::getVendorNomalInfoData,queryParams);
    }
    @GetMapping("/interface/getVendorManagerInfo-so")
    public Mono<String> getVendorManagerInfoData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestPRService::getVendorManagerInfoData,queryParams);
    }
    @GetMapping("/interface/getContractInfo-so")
    public Mono<String> getContractInfoData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestPRService::getContractInfoData,queryParams);
    }
    @GetMapping("/interface/getContractMonthlyPayInfo-so")
    public Mono<String> getContractMonthlyPayInfoData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestPRService::getContractMonthlyPayInfoData,queryParams);
    }
    @GetMapping("/interface/getFIPurchaseCost-so")
    public Mono<String> getFIPurchaseCostData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestPRService::getFIPurchaseCostData,queryParams);
    }
    @GetMapping("/interface/getBusinessIncomePayConfirmInfo-so")
    public Mono<String> getBusinessIncomePayConfirmInfoData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestPRService::getBusinessIncomePayConfirmInfoData,queryParams);
    }

    @PostMapping("/interface/doBusinessIncomePayConfirm")
    public Mono<String> getDoBusinessIncomePayConfirmData(@RequestBody Map<String, Object> requestBody) {
        return doExecute(restRequestPRService::getDoBusinessIncomePayConfirmData, requestBody);
    }


    @FunctionalInterface
    public interface ServiceCallerMap {
        Mono<String> call(Map<String, Object> param, String jwtToken);
    }

    @FunctionalInterface
    public interface ServiceCallerMultiValueMap {
        Mono<String> call(MultiValueMap<String, String> param, String jwtToken);
    }

    private <T> Mono<String> doExecute(Api2PRController.ServiceCallerMap serviceCaller, Map<String, Object> param) {
        return extractJwtFromContext()
                .flatMap(jwtToken -> serviceCaller.call(param, jwtToken));
    }

    private <T> Mono<String> doExecute(Api2ErpController.ServiceCallerMultiValueMap serviceCaller, MultiValueMap<String, String> param) {
        return extractJwtFromContext()
                .flatMap(jwtToken -> serviceCaller.call(param, jwtToken));
    }

    private Mono<String> extractJwtFromContext() {
        return Mono.deferContextual(ctx -> {
            String jwtToken = ctx.get("sys-access-token"); //JWTFilter에서 세팅한 context 네임
            if (jwtToken != null) {
                return Mono.just(jwtToken);
            } else {
                return Mono.error(new RuntimeException("JWT Token not found in context"));
            }
        });
    }
}
