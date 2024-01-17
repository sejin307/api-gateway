package com.devops.api2.gateway.locator;

import com.devops.api2.gateway.filter.CustomCircuitBreakerGatewayFilterFactory;
import com.devops.api2.gateway.filter.CustomFilter;
import com.devops.api2.gateway.locator.definition.BaseGatewayProperties;
import com.devops.api2.gateway.locator.provider.CustomCircuitBreakerConfigProvider;
import com.devops.api2.gateway.locator.provider.FilterListProvider;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO:cloud gateway 의 설정을 동적으로 가져와야함.
 * gateway에서 제공하는 메소드 사용법의 이해도가 없다면 코드작성이 매우 어려움.
 * 위의 이유로 Rocator에서는 yml정의를 java에서 동적생성을 할수없고, API마다 직접 Route지정
 *
 * 구매포탈에서 제공하는 API를 정의
 */
@Configuration
public class PRRouteLocator {


    private final CustomCircuitBreakerGatewayFilterFactory customCircuitBreakerFilterFactory;

    private final CustomFilter customFilter;

    private final BaseGatewayProperties baseGatewayProperties;
    private final CustomCircuitBreakerConfigProvider customCircuitBreakerConfigProvider;

    private final FilterListProvider filterListProvider;
    
    @Value("${targetPRPath}")
    private String targetPRPath;

    public PRRouteLocator(CustomCircuitBreakerGatewayFilterFactory customCircuitBreakerFilterFactory, CustomFilter customFilter, BaseGatewayProperties baseGatewayProperties, CustomCircuitBreakerConfigProvider customCircuitBreakerConfigProvider, FilterListProvider filterListProvider) {
        this.customCircuitBreakerFilterFactory = customCircuitBreakerFilterFactory;
        this.customFilter = customFilter;
        this.baseGatewayProperties = baseGatewayProperties;
        this.customCircuitBreakerConfigProvider = customCircuitBreakerConfigProvider;
        this.filterListProvider = filterListProvider;
    }

    @Bean
    public RouteLocator prRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("getVendorBuyerInfo-so", r -> r.path("/interface/getVendorBuyerInfo.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceVendorBuyerInfoCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .route("getVendorNomalInfo-so", r -> r.path("/interface/getVendorNomalInfo.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceVendorNomalInfoCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .route("getVendorManagerInfo-so", r -> r.path("/interface/getVendorManagerInfo.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceVendorManagerInfoCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .route("getContractInfo-so", r -> r.path("/interface/getContractInfo.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceContractInfoCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .route("getContractMonthlyPayInfo-so", r -> r.path("/interface/getContractMonthlyPayInfo.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceContractMonthlyPayInfoCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .route("getFIPurchaseCost-so", r -> r.path("/interface/getFIPurchaseCost.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceFIPurchaseCostCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .route("getBusinessIncomePayConfirmInfo-so", r -> r.path("/interface/getBusinessIncomePayConfirmInfo.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceBusinessIncomePayConfirmInfoCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .route("doBusinessIncomePayConfirm-so", r -> r.path("/interface/doBusinessIncomePayConfirm.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceDoBusinessIncomePayConfirmCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .build();
    }

    private CustomCircuitBreakerGatewayFilterFactory.Config getFilterConfig(@Nullable String filterName){
        CustomCircuitBreakerGatewayFilterFactory.Config filterConfig = customCircuitBreakerConfigProvider.getConfig(filterName);
        return filterConfig;
    }
}
