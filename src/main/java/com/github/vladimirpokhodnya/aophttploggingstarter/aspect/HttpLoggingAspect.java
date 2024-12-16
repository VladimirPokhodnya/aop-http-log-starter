package com.github.vladimirpokhodnya.aophttploggingstarter.aspect;

import com.github.vladimirpokhodnya.aophttploggingstarter.config.HttpLoggingProperties;
import com.github.vladimirpokhodnya.aophttploggingstarter.service.HttpRequestService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class HttpLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(HttpLoggingAspect.class);

    private final HttpLoggingProperties properties;
    private final HttpRequestService requestService;

    public HttpLoggingAspect(HttpLoggingProperties properties, HttpRequestService requestService) {
        this.properties = properties;
        this.requestService = requestService;
    }

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logHttpRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        logIncomingRequestMinimal();

        Object response = joinPoint.proceed();

        if (properties.getLevel() == HttpLoggingProperties.LogLevel.MEDIUM) {
            logIncomingRequestHeaders();
        } else if (properties.getLevel() == HttpLoggingProperties.LogLevel.FULL) {
            logIncomingRequestHeaders();
            logRequestBody(joinPoint);
        }

        logOutgoingResponse(response);

        return response;
    }

    private void logIncomingRequestHeaders() {
        logger.info("[MEDIUM] Incoming headers: {}", requestService.getRequestHeaders());
    }

    private void logIncomingRequestMinimal() {
        logger.info("[MINIMAL] Incoming request: method={}, URL={}", requestService.getMethod(), requestService.getRequestURL());
    }

    private void logOutgoingResponse(Object response) {
        logger.info("[MINIMAL] Outgoing response: status={}", response);
    }

    private void logRequestBody(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String requestBody = requestService.getRequestBody(args);
        logger.info("[FULL] Incoming request body: {}", requestBody);
    }
}

