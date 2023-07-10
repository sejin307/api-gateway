package com.devops.api2.gateway.rest;

import com.devops.api2.gateway.service.RestRequestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class Api2Controller {

    private final RestRequestService restRequestService;

    public Api2Controller(RestRequestService restRequestService) {
        this.restRequestService = restRequestService;
    }

    @GetMapping("/hydra")
    public Mono<String> getHydraData() {
        return restRequestService.getHydra();
    }

    @GetMapping("/overload")
    public Mono<String> getOverloadData() {
        return restRequestService.getOverload();
    }

    @GetMapping("/ultra")
    public Mono<String> getUltraData() {
        return restRequestService.getUltra();
    }
}


