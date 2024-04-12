package com.devops.api2.gateway.rest;

import com.devops.api2.gateway.service.RestRequestCenERPService;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api2")
public class Api2ErpController {

    private final RestRequestCenERPService restRequestCenERPService;

    public Api2ErpController(RestRequestCenERPService restRequestCenERPService) {
        this.restRequestCenERPService = restRequestCenERPService;
    }

    @GetMapping("/openapi/baseInfo")
    public Mono<String> getBaseInfoData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getBaseInfoData,queryParams);
    }

    @GetMapping("/openapi/dept")
    public Mono<String> getDeptData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getDeptData,queryParams);
    }

    @GetMapping("/openapi/company")
    public Mono<String> getCompanyData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getCompanyData,queryParams);
    }

    @GetMapping("/openapi/acntinfo")
    public Mono<String> getAcntinfoData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getAcntinfoData,queryParams);
    }

    @GetMapping("/openapi/acnts-map")
    public Mono<String> getAcntsmapData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getAcntsmapData,queryParams);
    }

    @GetMapping("/openapi/ar-collects")
    public Mono<String> getArcollectsData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getArcollectsData,queryParams);
    }

    @GetMapping("/openapi/vendor-bonds")
    public Mono<String> getVendorbondsData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getVendorbondsData,queryParams);
    }

    @GetMapping("/openapi/slipinfos")
    public Mono<String> getSlipinfosData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getSlipinfosData,queryParams);
    }

    @GetMapping("/openapi/vendors")
    public Mono<String> getVendorsData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getVendorsData,queryParams);
    }

    @GetMapping("/openapi/vendors-charges")
    public Mono<String> getVendorschargesData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getVendorschargesData,queryParams);
    }

    @GetMapping("/openapi/magaminfo-cenerp")
    public Mono<String> getMagaminfoCenerpData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getMagaminfoCenerpData,queryParams);
    }

    @GetMapping("/openapi/magam-vendor-bonds")
    public Mono<String> getMagamVendorBondsData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getMagamVendorBondsData,queryParams);
    }

    @GetMapping("/openapi/orderDeptMove")
    public Mono<String> getOrderdeptmoveData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getOrderdeptmoveData,queryParams);
    }

    @GetMapping("/openapi/dirct_persexp")
    public Mono<String> getDirctPersExpData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getDirctPersExpData,queryParams);
    }

    @GetMapping("/openapi/dept-acnt-info")
    public Mono<String> getDeptAcntInfoData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getDeptAcntInfoData,queryParams);
    }

    @GetMapping("/openapi/magaminfo-final-cenerp")
    public Mono<String> getMagamInfoFinalCenerpData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getMagamInfoFinalCenerpData,queryParams);
    }

    @GetMapping("/openapi/project-amt-info")
    public Mono<String> getProjectamtinfoData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getProjectamtinfoData,queryParams);
    }

    @GetMapping("/openapi/magaminfo-cenpcs")
    public Mono<String> getMagaminfocenpcsData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getMagaminfocenpcsData,queryParams);
    }

    @GetMapping("/openapi/taxes")
    public Mono<String> getTaxesData(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getTaxesData,queryParams);
    }

    @GetMapping("/openapi/magam-pl-bonds")
    public Mono<String> getMagamplbonds(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getMagamplbondsData, queryParams);
    }

    @PostMapping("/openapi/hometax-status")
    public Mono<String> getHometaxstatus(@RequestBody Map<String, Object> requestBody) {
        return doExecute(restRequestCenERPService::getHometaxstatusData, requestBody);
    }


    @GetMapping("/openapi/cost-project-info")
    public Mono<String> getCostProjectInfo(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getCostProjectInfoData, queryParams);
    }

    @GetMapping("/openapi/reverse-taxes")
    public Mono<String> getReversetaxes(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getReversetaxesData, queryParams);
    }

    @GetMapping("/openapi/dept_persexp")
    public Mono<String> getDeptpersexp(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getDeptpersexpData, queryParams);
    }

    @GetMapping("/openapi/exchrate_info")
    public Mono<String> getExchrateinfo(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getExchrateinfoData, queryParams);
    }


    @PostMapping("/openapi/slipinfos-post")
    public Mono<String> getSlipinfosPostData(@RequestBody Map<String, Object> requestBody) {
        return doExecute(restRequestCenERPService::getSlipinfosPostData, requestBody);
    }

    @PostMapping("/openapi/reverse-taxes-post")
    public Mono<String> getReverseTaxesPostData(@RequestBody Map<String, Object> requestBody) {
        return doExecute(restRequestCenERPService::getReverseTaxesPostData, requestBody);
    }

    @GetMapping("/openapi/contUsers-do")
    public Mono<String> getContUsersDo(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getContUsersDoData, queryParams);
    }

    @GetMapping("/openapi/order_info")
    public Mono<String> getOrderInfo(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getOrderInfoData, queryParams);
    }

    /**
     * 시큐센 인사정보연동
     */
    @GetMapping("/openapi/dept_secucen")
    public Mono<String> getDeptSecucen(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getDeptSecucenData, queryParams);
    }

    @GetMapping("/openapi/baseinfo_secucen")
    public Mono<String> getBaseinfoSecucen(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getBaseinfoSecucenData, queryParams);
    }

    @GetMapping("/openapi/order_mm")
    public Mono<String> getOrderMM(@RequestParam MultiValueMap<String, String> queryParams) {
        return doExecute(restRequestCenERPService::getOrderMMData, queryParams);
    }


    @FunctionalInterface
    public interface ServiceCallerMap {
        Mono<String> call(Map<String, Object> param, String jwtToken);
    }


    @FunctionalInterface
    interface ServiceCallerMultiValueMap {
        Mono<String> call(MultiValueMap<String, String> param, String jwtToken);
    }

    private <T> Mono<String> doExecute(ServiceCallerMap serviceCaller, Map<String, Object> param) {
        return extractJwtFromContext()
                .flatMap(jwtToken -> serviceCaller.call(param, jwtToken));
    }

    private <T> Mono<String> doExecute(ServiceCallerMultiValueMap serviceCaller, MultiValueMap<String, String> param) {
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


