package com.devops.api2.gateway.locator.definition;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * id,path,uri,predicates 등 external-routes-xxxx.yml 에 필요한 설정 추가
 * https://cloud.spring.io/spring-cloud-gateway/reference/html/
 * 공식 스펙 참고
 */
//@ConfigurationProperties(prefix = "custom")
public class RouteDefinition {
    private List<Route> routes = new ArrayList<>();

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public static class Route {
        private String id;
        private String path;
        private URI uri;
        private List<String> predicates;
        private List<String> defaultFilters;
        private List<Filter> filters;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public URI getUri() {
            return uri;
        }

        public void setUri(URI uri) {
            this.uri = uri;
        }

        public List<String> getPredicates() {
            return predicates;
        }

        public void setPredicates(List<String> predicates) {
            this.predicates = predicates;
        }

        public List<String> getDefaultFilters() {
            return defaultFilters;
        }

        public void setDefaultFilters(List<String> defaultFilters) {
            this.defaultFilters = defaultFilters;
        }

        public List<Filter> getFilters() {
            return filters;
        }

        public void setFilters(List<Filter> filters) {
            this.filters = filters;
        }
    }

    public static class Filter {
        private String name;
        private Map<String, String> args;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, String> getArgs() {
            return args;
        }

        public void setArgs(Map<String, String> args) {
            this.args = args;
        }
    }
}

