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
    private String erpPath;
    private String centerrPath;
    private String prPath;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getErpPath() {
        return erpPath;
    }

    public void setErpPath(String hydraPath) {
        this.erpPath = hydraPath;
    }

    public String getCENTerrPath() {
        return centerrPath;
    }

    public void setCENTerrPath(String overloadPath) {
        this.centerrPath = overloadPath;
    }

    public String getPrPath() {
        return prPath;
    }

    public void setPrPath(String ultraPath) {
        this.prPath = ultraPath;
    }
}
