package com.devops.api2.gateway.rest;

import com.devops.api2.gateway.service.RestRequestCenERPService;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api2")
public class Api2ErpController {

    private final RestRequestCenERPService restRequestCenERPService;

    public Api2ErpController(RestRequestCenERPService restRequestCenERPService) {
        this.restRequestCenERPService = restRequestCenERPService;
    }

    @GetMapping("/openapi/baseInfo")
    public Mono<String> getBaseInfoData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getBaseInfoData(queryParams);
    }

    @GetMapping("/openapi/dept")
    public Mono<String> getDeptData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getDeptData(queryParams);
    }

    @GetMapping("/openapi/company")
    public Mono<String> getCompanyData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getCompanyData(queryParams);
    }

    @GetMapping("/openapi/acntinfo")
    public Mono<String> getAcntinfoData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getAcntinfoData(queryParams);
    }

    @GetMapping("/openapi/acnts-map")
    public Mono<String> getAcntsmapData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getAcntsmapData(queryParams);
    }

    @GetMapping("/openapi/ar-collects")
    public Mono<String> getArcollectsData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getArcollectsData(queryParams);
    }

    @GetMapping("/openapi/vendor-bonds")
    public Mono<String> getVendorbondsData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getVendorbondsData(queryParams);
    }

    @GetMapping("/openapi/slipinfos")
    public Mono<String> getSlipinfosData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getSlipinfosData(queryParams);
    }

    @GetMapping("/openapi/vendors")
    public Mono<String> getVendorsData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getVendorsData(queryParams);
    }

    @GetMapping("/openapi/vendors-charges")
    public Mono<String> getVendorschargesData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getVendorschargesData(queryParams);
    }

    @GetMapping("/openapi/magaminfo-cenerp")
    public Mono<String> getMagaminfoCenerpData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getMagaminfoCenerpData(queryParams);
    }

    @GetMapping("/openapi/magam-vendor-bonds")
    public Mono<String> getMagamVendorBondsData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getMagamVendorBondsData(queryParams);
    }

    @GetMapping("/openapi/orderDeptMove")
    public Mono<String> getOrderdeptmoveData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getOrderdeptmoveData(queryParams);
    }

    @GetMapping("/openapi/dirct_persexp")
    public Mono<String> getDirctPersExpData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getDirctPersExpData(queryParams);
    }

    @GetMapping("/openapi/dept-acnt-info")
    public Mono<String> getDeptAcntInfoData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getDeptAcntInfoData(queryParams);
    }

    @GetMapping("/openapi/magaminfo-final-cenerp")
    public Mono<String> getMagamInfoFinalCenerpData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getMagamInfoFinalCenerpData(queryParams);
    }

    @GetMapping("/openapi/project-amt-info")
    public Mono<String> getProjectamtinfoData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getProjectamtinfoData(queryParams);
    }

    @GetMapping("/openapi/magaminfo-cenpcs")
    public Mono<String> getMagaminfocenpcsData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getMagaminfocenpcsData(queryParams);
    }

    @GetMapping("/openapi/taxes")
    public Mono<String> getTaxesData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestCenERPService.getTaxesData(queryParams);
    }
}


