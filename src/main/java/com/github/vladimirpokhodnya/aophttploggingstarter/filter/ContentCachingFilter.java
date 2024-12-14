package com.github.vladimirpokhodnya.aophttploggingstarter.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ContentCachingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(ContentCachingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);

        chain.doFilter(wrappedRequest, response);

        String requestBody = getRequestBody(wrappedRequest);

        if(requestBody.length() > 0) {
            logger.info("Request Body: " + requestBody);
        } else {
            logger.info("No Request Body");
        }

    }

    public String getRequestBody(ContentCachingRequestWrapper request) {
        byte[] content = request.getContentAsByteArray();
        return new String(content, StandardCharsets.UTF_8);
    }

}