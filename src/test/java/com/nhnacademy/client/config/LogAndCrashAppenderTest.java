package com.nhnacademy.client.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class LogAndCrashAppenderTest {

    private LogAndCrashAppender appender;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ILoggingEvent loggingEvent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        appender = new LogAndCrashAppender();
        appender.setAppKey("testAppKey");
        appender.setPlatform("testPlatform");
        ReflectionTestUtils.setField(appender, "restTemplate", restTemplate);
    }

    @Test
    void testAppend_SuccessfulLog() {
        when(loggingEvent.getFormattedMessage()).thenReturn("Test log message");
        when(loggingEvent.getLevel()).thenReturn(Level.INFO);
        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), eq(String.class)))
                .thenReturn("Success");

        appender.append(loggingEvent);

        verify(restTemplate, times(1)).postForObject(
                eq("https://api-logncrash.cloud.toast.com/v2/log"),
                argThat(entity -> {
                    HttpEntity<?> httpEntity = (HttpEntity<?>) entity;
                    HttpHeaders headers = httpEntity.getHeaders();
                    return headers.getContentType().equals(MediaType.APPLICATION_JSON) &&
                            headers.getAccept().contains(MediaType.APPLICATION_JSON);
                }),
                eq(String.class)
        );
    }

    @Test
    void testAppend_WithException() {
        Exception testException = new RuntimeException("Test exception");
        ThrowableProxy throwableProxy = new ThrowableProxy(testException);

        when(loggingEvent.getFormattedMessage()).thenReturn("Test log message");
        when(loggingEvent.getLevel()).thenReturn(Level.ERROR);
        when(loggingEvent.getThrowableProxy()).thenReturn(throwableProxy);
        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), eq(String.class)))
                .thenReturn("Success");

        appender.append(loggingEvent);

        verify(restTemplate, times(1)).postForObject(
                eq("https://api-logncrash.cloud.toast.com/v2/log"),
                argThat(entity -> {
                    HttpEntity<?> httpEntity = (HttpEntity<?>) entity;
                    @SuppressWarnings("unchecked")
                    Map<String, Object> body = (Map<String, Object>) httpEntity.getBody();
                    return body != null &&
                            body.get("body").toString().contains("Test log message") &&
                            body.get("body").toString().contains("Test exception");
                }),
                eq(String.class)
        );
    }

    @Test
    void testAppend_FailedLog() {
        when(loggingEvent.getFormattedMessage()).thenReturn("Test log message");
        when(loggingEvent.getLevel()).thenReturn(Level.INFO);
        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), eq(String.class)))
                .thenThrow(new RuntimeException("Network error"));

        appender.append(loggingEvent);

        verify(restTemplate, times(1)).postForObject(
                eq("https://api-logncrash.cloud.toast.com/v2/log"),
                any(HttpEntity.class),
                eq(String.class)
        );
    }
}