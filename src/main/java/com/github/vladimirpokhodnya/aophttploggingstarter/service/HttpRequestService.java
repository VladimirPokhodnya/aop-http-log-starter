package com.github.vladimirpokhodnya.aophttploggingstarter.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
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


    public String getRequestBody() {
        return "***BODY***";
    }
}