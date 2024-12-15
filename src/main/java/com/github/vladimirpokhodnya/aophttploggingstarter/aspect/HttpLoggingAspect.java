package com.github.vladimirpokhodnya.aophttploggingstarter.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vladimirpokhodnya.aophttploggingstarter.config.HttpLoggingProperties;
import com.github.vladimirpokhodnya.aophttploggingstarter.service.HttpRequestService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class HttpLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(HttpLoggingAspect.class);

    private final HttpLoggingProperties properties;
    private final HttpServletRequest request;
    private final HttpRequestService requestService;

    private final ObjectMapper objectMapper;

    public HttpLoggingAspect(HttpLoggingProperties properties, HttpServletRequest request, HttpRequestService requestService, ObjectMapper objectMapper) {
        this.properties = properties;
        this.request = request;
        this.requestService = requestService;
        this.objectMapper = objectMapper;
    }

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logHttpRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("[MINIMAL] Incoming request: method={}, URL={}", request.getMethod(), request.getRequestURL());

        if(properties.getLevel() == HttpLoggingProperties.LogLevel.MEDIUM ||
           properties.getLevel() == HttpLoggingProperties.LogLevel.FULL) {
            logger.info("[MEDIUM] Incoming headers: {}", requestService.getRequestHeaders());
        }

        Object response = joinPoint.proceed();

        if(properties.getLevel() == HttpLoggingProperties.LogLevel.FULL) {
            Object[] args = joinPoint.getArgs();
            String requestBody = null;

            for (Object arg : args) {
                if (arg != null) {
                    try {
                        requestBody = objectMapper.writeValueAsString(arg);
                    } catch (Exception e) {
                        logger.warn("Could not parse request body to JSON", e);
                    }
                }
            }
            logger.info("[FULL] Incoming request body: {}", requestBody);
        }

        logger.info("[MINIMAL] Outgoing response: status={}", response);

        return response;
    }

}