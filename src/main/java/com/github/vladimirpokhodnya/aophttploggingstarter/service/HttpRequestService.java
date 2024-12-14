package com.github.vladimirpokhodnya.aophttploggingstarter.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
@ConditionalOnProperty(name = "http.logging.enabled", havingValue = "true")
public class HttpRequestService {

    private final HttpServletRequest request;

    public HttpRequestService(HttpServletRequest request) {
        this.request = request;
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

}