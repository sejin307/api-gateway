package com.devops.api2.gateway.locator;

import com.devops.api2.gateway.filter.CustomCircuitBreakerGatewayFilterFactory;
import com.devops.api2.gateway.filter.CustomFilter;
import com.devops.api2.gateway.locator.definition.FilterDefinition;
import com.devops.api2.gateway.locator.definition.GatewayPropertiesPOJO;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * cloud gateway 의 설정을 동적으로 가져와야하는데 코드가 매우복잡하고,
 * 왠만한 gateway에서 제공하는 메소드 사용법의 이해도가 없다면 코드작성이 매우 어려움.
 * 위의 이유로 Rocator는 yml설정을 Java에서 동적생성 보다  Rocator에서 직접세팅
 */
@Configuration
public class CenERPRouteLocator {

    private final CustomCircuitBreakerGatewayFilterFactory customCircuitBreakerFilterFactory;

    private final CustomFilter customFilter;

    private final GatewayPropertiesPOJO gatewayPropertiesPOJO;

    public CenERPRouteLocator(CustomCircuitBreakerGatewayFilterFactory customCircuitBreakerFilterFactory, CustomFilter customFilter, GatewayPropertiesPOJO gatewayPropertiesPOJO) {
        this.customCircuitBreakerFilterFactory = customCircuitBreakerFilterFactory;
        this.customFilter = customFilter;
        this.gatewayPropertiesPOJO = gatewayPropertiesPOJO;
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        CustomCircuitBreakerGatewayFilterFactory.Config baseinfoConfig = new CustomCircuitBreakerGatewayFilterFactory.Config();
        baseinfoConfig.setName("erpServiceCircuitBreaker");

        CustomCircuitBreakerGatewayFilterFactory.Config deptConfig = new CustomCircuitBreakerGatewayFilterFactory.Config();
        deptConfig.setName("erpServiceCircuitBreaker");

        return builder.routes()
                .route("baseinfo", r -> r.path("/cenerp/openapi/baseinfo")
                        .filters(f -> f.filters(convertToFilters(customCircuitBreakerFilterFactory, customFilter, baseinfoConfig)))
                        .uri("http://127.0.0.1:8080"))
                .route("dept", r -> r.path("/cenerp/openapi/dept")
                        .filters(f -> f.filters(convertToFilters(customCircuitBreakerFilterFactory, customFilter, deptConfig)))
                        .uri("http://127.0.0.1:8080"))
                .build();
    }

    private List<GatewayFilter> convertToFilters(CustomCircuitBreakerGatewayFilterFactory customCircuitBreakerFilterFactory, CustomFilter customFilter, CustomCircuitBreakerGatewayFilterFactory.Config config) {
        List<GatewayFilter> filters = new ArrayList<>();
        List<FilterDefinition> defaultFilters = gatewayPropertiesPOJO.getDefaultFilters();

        if (defaultFilters != null) {
            // Add default filters based on the configuration
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
