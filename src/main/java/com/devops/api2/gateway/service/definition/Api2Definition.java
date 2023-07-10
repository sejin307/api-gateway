package com.devops.api2.gateway.service.definition;


import com.devops.api2.gateway.config.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:internal-api-config.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "api")
public class Api2Definition {

    private String baseUrl;
    private String hydraPath;
    private String overloadPath;
    private String ultraPath;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getHydraPath() {
        return hydraPath;
    }

    public void setHydraPath(String hydraPath) {
        this.hydraPath = hydraPath;
    }

    public String getOverloadPath() {
        return overloadPath;
    }

    public void setOverloadPath(String overloadPath) {
        this.overloadPath = overloadPath;
    }

    public String getUltraPath() {
        return ultraPath;
    }

    public void setUltraPath(String ultraPath) {
        this.ultraPath = ultraPath;
    }
}
