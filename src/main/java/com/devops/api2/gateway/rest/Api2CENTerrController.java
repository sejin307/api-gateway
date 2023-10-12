package com.devops.api2.gateway.rest;

import com.devops.api2.gateway.service.RestRequestCENTerrService;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api2")
public class Api2CENTerrController {

    private final RestRequestCENTerrService restRequestCENTerrService;

    public Api2CENTerrController(RestRequestCENTerrService restRequestCENTerrService) {
        this.restRequestCENTerrService = restRequestCENTerrService;
    }

//    @GetMapping("/api/pms/inf/act-plan-activity-hnfs")
//    public Mono<String> getIFPMSERP01Data(@RequestParam MultiValueMap<String, String> queryParams) {
//        return restRequestCENTerrService.getIFPMSERP01Data(queryParams);
//    }
}


