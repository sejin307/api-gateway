package com.devops.api2.gateway.rest;

import com.devops.api2.gateway.service.RestRequestService;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api2")
public class Api2ErpController {

    private final RestRequestService restRequestService;

    public Api2ErpController(RestRequestService restRequestService) {
        this.restRequestService = restRequestService;
    }

    @GetMapping("/openapi/baseinfo")
    public Mono<String> getBaseInfoData(@RequestParam MultiValueMap<String, String> queryParams) {
        return restRequestService.getBaseInfoData(queryParams);
    }


}


