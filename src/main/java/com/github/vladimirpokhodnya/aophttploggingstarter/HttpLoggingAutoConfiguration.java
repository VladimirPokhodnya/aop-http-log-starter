package com.github.vladimirpokhodnya.aophttploggingstarter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.github.vladimirpokhodnya.aophttploggingstarter")
public class HttpLoggingAutoConfiguration {
    @Bean
    public HttpLoggingAspect httpLoggingAspect(HttpServletRequest request) {
        return new HttpLoggingAspect(request);
    }
}
