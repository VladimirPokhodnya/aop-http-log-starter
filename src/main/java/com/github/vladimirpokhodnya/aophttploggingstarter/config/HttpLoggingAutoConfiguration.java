package com.github.vladimirpokhodnya.aophttploggingstarter.config;

import com.github.vladimirpokhodnya.aophttploggingstarter.aspect.HttpLoggingAspect;
import com.github.vladimirpokhodnya.aophttploggingstarter.filter.ContentCachingFilter;
import com.github.vladimirpokhodnya.aophttploggingstarter.service.HttpRequestService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.github.vladimirpokhodnya.aophttploggingstarter")
public class HttpLoggingAutoConfiguration {
    @Bean
    @ConditionalOnProperty(name = "http.logging.enabled", havingValue = "true")
    public HttpLoggingAspect httpLoggingAspect(HttpLoggingProperties properties, HttpServletRequest request,
                                               HttpRequestService requestService) {
        return new HttpLoggingAspect(properties, request, requestService);
    }

    @Bean
    @ConditionalOnProperty(name = "http.logging.level", havingValue = "FULL")
    public ContentCachingFilter contentCachingFilter() {
        return new ContentCachingFilter();
    }

}
