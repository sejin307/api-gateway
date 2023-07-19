package com.devops.api2.gateway.service.definition;

import com.devops.api2.gateway.config.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:internal-api-config.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "api.centerr")
public class Api2CENTerrDefinition {

    private String baseUrl;
    private String IFPMSERP01;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getIFPMSERP01() {
        return IFPMSERP01;
    }

    public void setIFPMSERP01(String IFPMSERP01) {
        this.IFPMSERP01 = IFPMSERP01;
    }
}
