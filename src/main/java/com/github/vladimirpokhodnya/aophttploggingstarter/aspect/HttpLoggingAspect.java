package com.github.vladimirpokhodnya.aophttploggingstarter.aspect;

import com.github.vladimirpokhodnya.aophttploggingstarter.config.HttpLoggingProperties;
import com.github.vladimirpokhodnya.aophttploggingstarter.service.HttpRequestService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;

@Aspect
public class HttpLoggingAspect {

private static final Logger logger = LogManager.getLogger(HttpLoggingAspect.class);

    private final HttpLoggingProperties properties;

    public static final Level MINIMAL = Level.forName("MINIMAL HTTP LOG", 350);
    public static final Level MEDIUM = Level.forName("MEDIUM HTTP LOG", 351);
    public static final Level FULL = Level.forName("FULL HTTP LOG", 352);

    private final HttpRequestService requestService;

    public HttpLoggingAspect(HttpLoggingProperties properties, HttpRequestService requestService) {
        this.properties = properties;
        this.requestService = requestService;
    }

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logHttpRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        logIncomingRequestInfo();

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
        logger.log(MEDIUM, "Incoming headers: {}", requestService.getRequestHeaders());
    }

    private void logIncomingRequestInfo() {
        logger.log(MINIMAL,"Incoming request: method={}, URL={}", requestService.getMethod(), requestService.getRequestURL());
    }

    private void logOutgoingResponse(Object response) {
        logger.log(MINIMAL,"Outgoing response: {}", response);
    }

    private void logRequestBody(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String requestBody = requestService.getRequestBody(args);
        logger.log(FULL,"Incoming request body: {}", requestBody);
    }
}

