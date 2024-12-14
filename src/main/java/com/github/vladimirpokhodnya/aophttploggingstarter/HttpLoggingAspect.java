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

    public HttpLoggingAspect(HttpLoggingProperties properties, HttpServletRequest request) {
        this.properties = properties;
        this.request = request;
    }

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logHttpRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!properties.isEnabled()) {
            return joinPoint.proceed();
        }

        logger.info("Incoming request: method={}, URI={}", request.getMethod(), request.getRequestURI());

        Object response = joinPoint.proceed();

        logger.info("Outgoing response: status={}", response);

        return response;
    }
}