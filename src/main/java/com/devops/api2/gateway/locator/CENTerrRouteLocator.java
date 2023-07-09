package com.devops.api2.gateway.locator;


import com.devops.api2.gateway.config.YamlPropertySourceFactory;
import com.devops.api2.gateway.locator.definition.RouteDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:external-routes-centerr.yml", factory = YamlPropertySourceFactory.class)
@EnableConfigurationProperties(RouteDefinition.class)
public class CENTerrRouteLocator {

    private final RouteDefinition routeProperties;

    public CENTerrRouteLocator(RouteDefinition routeProperties) {
        this.routeProperties = routeProperties;
    }

    public RouteLocator CENTerrRouteLocator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routesBuilder = builder.routes();
        for (RouteDefinition.Route route : routeProperties.getRoutes()) {
            routesBuilder.route(route.getId(), r -> r.path(route.getPredicates().get(0)).uri(route.getUri()));
        }
        return routesBuilder.build();
    }
}
