package com.devops.api2.gateway.fallback;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @RequestMapping("/fallbackURI")
    public ResponseEntity<String> returnFallbackMessage() {
        return new ResponseEntity<>("Target Service is down. Please try again later.", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
