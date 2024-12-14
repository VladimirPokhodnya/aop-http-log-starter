package com.github.vladimirpokhodnya.aophttploggingstarter;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HttpLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(HttpLoggingAspect.class);

    private final HttpLoggingProperties properties;
    private final HttpServletRequest request;
    private final HttpRequestService requestService;


    public HttpLoggingAspect(HttpLoggingProperties properties, HttpServletRequest request, HttpRequestService requestService) {
        this.properties = properties;
        this.request = request;
        this.requestService = requestService;
    }

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logHttpRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!properties.isEnabled()) {
            return joinPoint.proceed();
        }

        logger.info("Incoming request: method={}, URI={}", request.getMethod(), request.getRequestURI());

        if(properties.getLevel() == HttpLoggingProperties.LogLevel.MEDIUM  ||
           properties.getLevel() == HttpLoggingProperties.LogLevel.FULL) {
            logger.info("Incoming headers: {}", requestService.getRequestHeaders());
        }

        if(properties.getLevel() == HttpLoggingProperties.LogLevel.FULL) {
            logger.info("Request body: {}", requestService.getRequestBody());
        }

        Object response = joinPoint.proceed();

        logger.info("Outgoing response: status={}", response);

        return response;
    }
}