package com.devops.api2.gateway.config;

import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Properties;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.util.StringUtils;

/**
 * external-routes-xxxxxx.yml
 * internal-api-config.yml
 * 파일 로딩
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws FileNotFoundException {
        Properties yamlProperties = loadYamlIntoProperties(resource);
        String sourceName = StringUtils.hasText(name) ? name : resource.getResource().getFilename();
        return new PropertiesPropertySource(Objects.requireNonNull(sourceName), Objects.requireNonNull(yamlProperties));
    }

    private Properties loadYamlIntoProperties(EncodedResource resource) throws FileNotFoundException {
        try {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(resource.getResource());
            factory.afterPropertiesSet();
            return factory.getObject();
        } catch (IllegalStateException ex) {
            // for ignoreResourceNotFound
            Throwable cause = ex.getCause();
            if (cause instanceof FileNotFoundException)
                throw (FileNotFoundException) ex.getCause();
            throw ex;
        }
    }
}

