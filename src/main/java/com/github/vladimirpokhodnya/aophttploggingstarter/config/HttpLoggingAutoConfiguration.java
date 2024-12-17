package com.github.vladimirpokhodnya.aophttploggingstarter.config;

import com.github.vladimirpokhodnya.aophttploggingstarter.aspect.HttpLoggingAspect;
import com.github.vladimirpokhodnya.aophttploggingstarter.service.HttpRequestService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(HttpLoggingProperties.class)
public class HttpLoggingAutoConfiguration {

    @Bean
    @ConditionalOnProperty(name = "http.logging.enabled", havingValue = "true", matchIfMissing = true)
    public HttpLoggingAspect httpLoggingAspect(HttpLoggingProperties properties,
                                               HttpRequestService requestService) {
        return new HttpLoggingAspect(properties, requestService);
    }

}
