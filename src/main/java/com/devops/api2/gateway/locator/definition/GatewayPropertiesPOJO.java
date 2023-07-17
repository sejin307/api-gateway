package com.devops.api2.gateway.locator.definition;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * System 기동시 CustomFilter를 주입하기위함
 */
@Component
@ConfigurationProperties(prefix = "spring.cloud.gateway")
public class GatewayPropertiesPOJO {

    private List<FilterDefinition> defaultFilters;

    public List<FilterDefinition> getDefaultFilters() {
        return defaultFilters;
    }

    public void setDefaultFilters(List<FilterDefinition> defaultFilters) {
        this.defaultFilters = defaultFilters;
    }
}


