package com.devops.api2.gateway.locator.provider;

import com.devops.api2.gateway.filter.CustomCircuitBreakerGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomCircuitBreakerConfigProvider {
    public CustomCircuitBreakerGatewayFilterFactory.Config getConfig(String name) {
        CustomCircuitBreakerGatewayFilterFactory.Config config = new CustomCircuitBreakerGatewayFilterFactory.Config();
        config.setName(name);
        return config;
    }
}
