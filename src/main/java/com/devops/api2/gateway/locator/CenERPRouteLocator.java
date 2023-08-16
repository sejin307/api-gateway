package com.devops.api2.gateway.locator;

import com.devops.api2.gateway.filter.CustomCircuitBreakerGatewayFilterFactory;
import com.devops.api2.gateway.filter.CustomFilter;
import com.devops.api2.gateway.locator.definition.GatewayPropertiesPOJO;
import com.devops.api2.gateway.locator.provider.CustomCircuitBreakerConfigProvider;
import com.devops.api2.gateway.locator.provider.FilterListProvider;
import jakarta.annotation.Nullable;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * TODO:cloud gateway 의 설정을 동적으로 가져와야함.
 * 왠만한 gateway에서 제공하는 메소드 사용법의 이해도가 없다면 코드작성이 매우 어려움.
 * 위의 이유로 Rocator는 yml설정을 Java에서 동적생성 보다  Locator에서 직접세팅
 *
 * CENERP에서 제공하는 API를 정의
 */
@Configuration
public class CenERPRouteLocator {

    private final CustomCircuitBreakerGatewayFilterFactory customCircuitBreakerFilterFactory;

    private final CustomFilter customFilter;

    private final GatewayPropertiesPOJO gatewayPropertiesPOJO;

    private final CustomCircuitBreakerConfigProvider customCircuitBreakerConfigProvider;

    private final FilterListProvider filterListProvider;

    private final String internalDevUrl = "http://192.168.63.71:8080";
    private final String publicDevUrl = "http://121.138.156.45:8080";
    private final String publicProdUrl = "https://erp.cengroup.co.kr";

    public CenERPRouteLocator(CustomCircuitBreakerGatewayFilterFactory customCircuitBreakerFilterFactory, CustomFilter customFilter, GatewayPropertiesPOJO gatewayPropertiesPOJO, CustomCircuitBreakerConfigProvider customCircuitBreakerConfigProvider, FilterListProvider filterListProvider) {
        this.customCircuitBreakerFilterFactory = customCircuitBreakerFilterFactory;
        this.customFilter = customFilter;
        this.gatewayPropertiesPOJO = gatewayPropertiesPOJO;
        this.customCircuitBreakerConfigProvider = customCircuitBreakerConfigProvider;
        this.filterListProvider = filterListProvider;
    }

    @Bean
    public RouteLocator erpRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("baseinfo", r -> r.path("/cenerp/openapi/baseInfo")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("erpServiceBaseInfoCircuitBreaker")).toArray(new GatewayFilter[0])))
                        .uri(publicDevUrl))
                .route("dept", r -> r.path("/cenerp/openapi/dept")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("erpServiceDeptCircuitBreaker"))
                                    .toArray(new GatewayFilter[0])))
                        .uri(publicDevUrl))
                .route("company", r -> r.path("/cenerp/openapi/company")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("erpServiceCompanyCircuitBreaker"))
                                    .toArray(new GatewayFilter[0])))
                        .uri(publicDevUrl))
                .route("acntinfo", r -> r.path("/cenerp/openapi/acntinfo")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                getFilterConfig("erpServiceAcntinfoCircuitBreaker"))
                                    .toArray(new GatewayFilter[0])))
                        .uri(publicDevUrl))
                .route("acnts-map", r -> r.path("/cenerp/openapi/acnts-map")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                 getFilterConfig("erpServiceAcntsmapCircuitBreaker"))
                                    .toArray(new GatewayFilter[0])))
                        .uri(publicDevUrl))
                .route("ar-collects", r -> r.path("/cenerp/openapi/ar-collects")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                 getFilterConfig("erpServiceArcollectsCircuitBreaker"))
                                    .toArray(new GatewayFilter[0])))
                        .uri(publicDevUrl))
                .route("vendor-bonds", r -> r.path("/cenerp/openapi/vendor-bonds")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                 getFilterConfig("erpServiceVendorbondsCircuitBreaker"))
                                    .toArray(new GatewayFilter[0])))
                        .uri(publicDevUrl))
                .route("slipinfos", r -> r.path("/cenerp/openapi/slipinfos")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                 getFilterConfig("erpServiceSlipinfosCircuitBreaker"))
                                    .toArray(new GatewayFilter[0])))
                        .uri(publicDevUrl))
                .route("vendors", r -> r.path("/cenerp/openapi/vendors")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                 getFilterConfig("erpServiceVendorsCircuitBreaker"))
                                    .toArray(new GatewayFilter[0])))
                        .uri(publicDevUrl))
                .route("vendors-charges", r -> r.path("/cenerp/openapi/vendors-charges")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                 getFilterConfig("erpServiceVendorschargesCircuitBreaker"))
                                    .toArray(new GatewayFilter[0])))
                        .uri(publicDevUrl))
                .route("magaminfo-cenerp", r -> r.path("/cenerp/openapi/magaminfo-cenerp")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceVendorschargesCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(publicDevUrl))
                .route("magam-vendor-bonds", r -> r.path("/cenerp/openapi/magam-vendor-bonds")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceMagamVendorBondsCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(publicDevUrl))
                .route("orderDeptMove", r -> r.path("/cenerp/openapi/orderDeptMove")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceorderDeptMoveCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(publicDevUrl))
                .route("dirct_persexp", r -> r.path("/cenerp/openapi/dirct_persexp")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceDirctPersExpCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(publicDevUrl))
                .route("dept-acnt-info", r -> r.path("/cenerp/openapi/dept-acnt-info")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceDeptAcntInfoCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(publicDevUrl))
                .route("magaminfo-final-cenerp", r -> r.path("/cenerp/openapi/magaminfo-final-cenerp")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceMagamInfoFinalCenerpCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(publicDevUrl))
                .route("project-amt-info", r -> r.path("/cenerp/openapi/project-amt-info")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceProjectamtinfoCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(publicDevUrl))
                .route("magaminfo-cenpcs", r -> r.path("/cenerp/openapi/magaminfo-cenpcs")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceMagaminfocenpcsCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(publicDevUrl))
                .route("taxes", r -> r.path("/cenerp/openapi/taxes")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceTaxesCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(publicDevUrl))
                .route("magam-pl-bonds", r -> r.path("/cenerp/openapi/magam-pl-bonds")
                        .filters(f -> f.filters(filterListProvider.getFilters(customCircuitBreakerFilterFactory, customFilter,
                                        getFilterConfig("erpServiceMagamplbondsCircuitBreaker"))
                                .toArray(new GatewayFilter[0])))
                        .uri(publicDevUrl))
                .build();
    }

    private CustomCircuitBreakerGatewayFilterFactory.Config getFilterConfig(@Nullable String filterName){
        CustomCircuitBreakerGatewayFilterFactory.Config filterConfig = customCircuitBreakerConfigProvider.getConfig(filterName);
        return filterConfig;
    }
}
