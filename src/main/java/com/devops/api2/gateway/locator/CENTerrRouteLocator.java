package com.devops.api2.gateway.locator;


import com.devops.api2.gateway.config.YamlPropertySourceFactory;
import com.devops.api2.gateway.locator.definition.Api2RouteLocator;
import com.devops.api2.gateway.locator.definition.RouteDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * 인터페이스API 라우팅에 대한 정의
 * application.yml > external-routes-system.yml > RouteDefinition > 파일로 읽어들임.
 */
@Configuration
@PropertySource(value = "classpath:external-routes-centerr.yml", factory = YamlPropertySourceFactory.class)
@EnableConfigurationProperties(RouteDefinition.class)
public class CENTerrRouteLocator implements Api2RouteLocator {

    private final RouteDefinition routeProperties;

    public CENTerrRouteLocator(RouteDefinition routeProperties) {
        this.routeProperties = routeProperties;
    }

    @Override
    public RouteLocator CenERPRouteLocator(RouteLocatorBuilder builder) {
        return null;
    }


    public RouteLocator CENTerrRouteLocator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routesBuilder = builder.routes();
        for (RouteDefinition.Route route : routeProperties.getRoutes()) {
            routesBuilder.route(route.getId(), r -> r.path(route.getPredicates().get(0)).uri(route.getUri()));
        }
        return routesBuilder.build();
    }

    @Override
    public RouteLocator PurchaseRouteLocator(RouteLocatorBuilder builder) {
        return null;
    }
}
