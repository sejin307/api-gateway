package com.devops.api2.gateway.locator.definition;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Configuration;

/**
 * RouteLocator 의 최상위
 */

public interface Api2RouteLocator {

    RouteLocator CenERPRouteLocator(RouteLocatorBuilder builder);

    RouteLocator CENTerrRouteLocator(RouteLocatorBuilder builder);

    RouteLocator PurchaseRouteLocator(RouteLocatorBuilder builder);
}
