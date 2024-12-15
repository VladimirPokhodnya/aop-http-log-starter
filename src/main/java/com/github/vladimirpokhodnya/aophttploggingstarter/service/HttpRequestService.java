package com.github.vladimirpokhodnya.aophttploggingstarter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
@ConditionalOnProperty(name = "http.logging.enabled", havingValue = "true")
public class HttpRequestService {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestService.class);

    private final HttpServletRequest request;
    private final ObjectMapper objectMapper;

    public HttpRequestService(HttpServletRequest request, ObjectMapper objectMapper) {
        this.request = request;
        this.objectMapper = objectMapper;
    }

    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
        }

        return headers;
    }

    public String getRequestBody(Object[] args) {
        StringBuilder requestBody = new StringBuilder();
        for (Object arg : args) {
            if (arg != null) {
                try {
                    requestBody.append(objectMapper.writeValueAsString(arg));
                } catch (Exception e) {
                    logger.warn("Could not parse request body to JSON", e);
                }
            }
        }
        return requestBody.toString();
    }

    public String getMethod() {
        return request.getMethod();
    }

    public String getRequestURL() {
        return request.getRequestURL().toString();
    }
}