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
                .route("getVendorBuyerInfo", r -> r.path("/interface/getVendorBuyerInfo.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceVendorBuyerInfoCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .route("getVendorNomalInfo", r -> r.path("/interface/getVendorNomalInfo.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceVendorNomalInfoCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .route("getVendorManagerInfo", r -> r.path("/interface/getVendorManagerInfo.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceVendorManagerInfoCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .route("getContractInfo", r -> r.path("/interface/getContractInfo.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceContractInfoCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .route("getContractMonthlyPayInfo", r -> r.path("/interface/getContractMonthlyPayInfo.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceContractMonthlyPayInfoCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .route("getFIPurchaseCost", r -> r.path("/interface/getFIPurchaseCost.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceFIPurchaseCostCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .route("getBusinessIncomePayConfirmInfo", r -> r.path("/interface/getBusinessIncomePayConfirmInfo.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceBusinessIncomePayConfirmInfoCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .route("doBusinessIncomePayConfirm", r -> r.path("/interface/doBusinessIncomePayConfirm.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceDoBusinessIncomePayConfirmCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .route("projectSave", r -> r.path("/interface/projectSave.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceProjectSaveCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .route("getItemTaxonomyInfo", r -> r.path("/interface/getItemTaxonomyInfo.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceGetItemTaxonomyInfoCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .route("getItemStandardInfo", r -> r.path("/interface/getItemStandardInfo.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceGetItemStandardInfoCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                /////////////////////////////////
                .route("cpResult", r -> r.path("/interface/cpResult.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceCpResultCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .route("giResult", r -> r.path("/interface/giResult.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceGiResultCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .route("getContractInfoCENTerr", r -> r.path("/interface/centerr/getContractInfo.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceGetContractInfoCENTerrCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .route("getContractMonthlyPayPlan", r -> r.path("/interface/centerr/getContractMonthlyPayPlan.so")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("prServiceGetContractMonthlyPayPlanCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(targetPRPath))
                .build();
    }

    private CustomCircuitBreakerGatewayFilterFactory.Config getFilterConfig(@Nullable String filterName){
        CustomCircuitBreakerGatewayFilterFactory.Config filterConfig = customCircuitBreakerConfigProvider.getConfig(filterName);
        return filterConfig;
    }
}
