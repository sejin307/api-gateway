package com.devops.api2.gateway.locator.provider;

import com.devops.api2.gateway.filter.CustomCircuitBreakerGatewayFilterFactory;
import com.devops.api2.gateway.filter.CustomFilter;
import com.devops.api2.gateway.locator.definition.FilterDefinition;
import com.devops.api2.gateway.locator.definition.GatewayPropertiesPOJO;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Gateway에서 사용할 default-filter 세팅
 *
 */
@Component
public class FilterListProvider {

    private final GatewayPropertiesPOJO gatewayPropertiesPOJO;

    public FilterListProvider(GatewayPropertiesPOJO gatewayPropertiesPOJO) {
        this.gatewayPropertiesPOJO = gatewayPropertiesPOJO;
    }

    public List<GatewayFilter> getFilters(CustomCircuitBreakerGatewayFilterFactory customCircuitBreakerFilterFactory, CustomFilter customFilter, CustomCircuitBreakerGatewayFilterFactory.Config config) {
        List<GatewayFilter> filters = new ArrayList<>();
        List<FilterDefinition> defaultFilters = gatewayPropertiesPOJO.getDefaultFilters();

        if (defaultFilters != null) {
            for (FilterDefinition filterName : defaultFilters){
                if("CustomFilter".equals(filterName)){
                    filters.add(customFilter.apply(new CustomFilter.Config()));
                }
            }
        }

        filters.add(customCircuitBreakerFilterFactory.apply(config));

        return filters;
    }
}
