package com.github.vladimirpokhodnya.aophttploggingstarter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "http.logging")
public class HttpLoggingProperties {
    private boolean enabled = true;
    private LogLevel level = LogLevel.MEDIUM;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public enum LogLevel {
        FULL,
        MEDIUM,
        MINIMAL
    }
}
