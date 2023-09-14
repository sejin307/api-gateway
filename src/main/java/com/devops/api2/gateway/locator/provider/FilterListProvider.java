package com.devops.api2.gateway.locator.provider;

import com.devops.api2.gateway.filter.CustomCircuitBreakerGatewayFilterFactory;
import com.devops.api2.gateway.filter.CustomFilter;
import com.devops.api2.gateway.locator.definition.FilterDefinition;
import com.devops.api2.gateway.locator.definition.BaseGatewayProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Gateway에서 사용할 default-filter 세팅
 *
 */
@Component
public class FilterListProvider {

    private final BaseGatewayProperties baseGatewayProperties;

    public FilterListProvider(BaseGatewayProperties baseGatewayProperties) {
        this.baseGatewayProperties = baseGatewayProperties;
    }

    /**
     * Routing 에 대한 Filter  > 전체 CustomFilter를 공통으로 사용하지만. Route건마다 필요에따라 Filter 를 추가하여 구현해야함.
     * @param customCircuitBreakerFilterFactory
     * @param customFilter
     * @param config
     * @return
     */
    public List<GatewayFilter> getFilters(CustomCircuitBreakerGatewayFilterFactory customCircuitBreakerFilterFactory, CustomFilter customFilter, CustomCircuitBreakerGatewayFilterFactory.Config config) {
        List<GatewayFilter> filters = new ArrayList<>();
        List<FilterDefinition> defaultFilters = baseGatewayProperties.getDefaultFilters();

        if (defaultFilters != null) {
            for (FilterDefinition filterName : defaultFilters){
                if(StringUtils.hasText(filterName.getName())){
                    filters.add(customFilter.apply(new CustomFilter.Config()));
                }
            }
        }

        filters.add(customCircuitBreakerFilterFactory.apply(config));

        return filters;
    }
}
