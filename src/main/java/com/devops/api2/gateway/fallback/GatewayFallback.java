package com.devops.api2.gateway.fallback;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayFallback {

    @RequestMapping("/GatewayServiceFallback")
    public ResponseEntity<String> returnFallbackMessage() {
        return new ResponseEntity<>("API2 Gateway Service Unavailable. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
