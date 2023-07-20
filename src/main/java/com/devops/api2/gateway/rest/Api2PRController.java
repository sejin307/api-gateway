package com.devops.api2.gateway.rest;


import com.devops.api2.gateway.service.RestRequestCenERPService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api2")
public class Api2PRController {

    private final RestRequestCenERPService restRequestCenERPService;

    public Api2PRController(RestRequestCenERPService restRequestCenERPService) {
        this.restRequestCenERPService = restRequestCenERPService;
    }
}
