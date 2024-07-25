package com.nhnacademy.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.client.client.KeyManagerClient;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestPropertySource(properties = {"user.access.key.id=test-access-id", "secret.access.key=test-access-secret"})
class KeyManagerConfigTest {

    @Mock
    private KeyManagerClient keyManagerClient;

    @InjectMocks
    private KeyManagerConfig keyManagerConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        keyManagerConfig = new KeyManagerConfig(keyManagerClient);
    }

    private JSONObject createMockResponse(String key) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("secret", key);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("body", bodyMap);

        return new JSONObject(responseMap);
    }

    @Test
    void testRedisKey() {
        when(keyManagerClient.getRedisHost(any(HttpHeaders.class))).thenReturn(createMockResponse("test-redis-host"));
        when(keyManagerClient.getRedisPassword(any(HttpHeaders.class))).thenReturn(createMockResponse("test-redis-password"));
        when(keyManagerClient.getRedisPort(any(HttpHeaders.class))).thenReturn(createMockResponse("6379"));
        when(keyManagerClient.getRedisDb(any(HttpHeaders.class))).thenReturn(createMockResponse("0"));

        Map<String, String> redisKey = keyManagerConfig.redisKey();

        assertEquals("test-redis-host", redisKey.get("host"));
        assertEquals("test-redis-password", redisKey.get("password"));
        assertEquals("6379", redisKey.get("port"));
        assertEquals("0", redisKey.get("db"));

        verify(keyManagerClient, times(1)).getRedisHost(any(HttpHeaders.class));
        verify(keyManagerClient, times(1)).getRedisPassword(any(HttpHeaders.class));
        verify(keyManagerClient, times(1)).getRedisPort(any(HttpHeaders.class));
        verify(keyManagerClient, times(1)).getRedisDb(any(HttpHeaders.class));
    }

    @Test
    void testRabbitKey() {
        when(keyManagerClient.getRabbitmqHost(any(HttpHeaders.class))).thenReturn(createMockResponse("test-rabbit-host"));
        when(keyManagerClient.getRabbitmqPassword(any(HttpHeaders.class))).thenReturn(createMockResponse("test-rabbit-password"));
        when(keyManagerClient.getRabbitmqUsername(any(HttpHeaders.class))).thenReturn(createMockResponse("test-rabbit-username"));
        when(keyManagerClient.getRabbitmqPort(any(HttpHeaders.class))).thenReturn(createMockResponse("5672"));

        Map<String, String> rabbitKey = keyManagerConfig.rabbitKey();

        assertEquals("test-rabbit-host", rabbitKey.get("host"));
        assertEquals("test-rabbit-password", rabbitKey.get("password"));
        assertEquals("test-rabbit-username", rabbitKey.get("username"));
        assertEquals("5672", rabbitKey.get("port"));

        verify(keyManagerClient, times(1)).getRabbitmqHost(any(HttpHeaders.class));
        verify(keyManagerClient, times(1)).getRabbitmqPassword(any(HttpHeaders.class));
        verify(keyManagerClient, times(1)).getRabbitmqUsername(any(HttpHeaders.class));
        verify(keyManagerClient, times(1)).getRabbitmqPort(any(HttpHeaders.class));
    }

    @Test
    void testMysqlKey() {
        when(keyManagerClient.getMysqlUrl(any(HttpHeaders.class))).thenReturn(createMockResponse("jdbc:mysql://localhost:3306/testdb"));
        when(keyManagerClient.getMysqlPassword(any(HttpHeaders.class))).thenReturn(createMockResponse("test-mysql-password"));
        when(keyManagerClient.getMysqlUsername(any(HttpHeaders.class))).thenReturn(createMockResponse("test-mysql-username"));

        Map<String, String> mysqlKey = keyManagerConfig.mysqlKey();

        assertEquals("jdbc:mysql://localhost:3306/testdb", mysqlKey.get("url"));
        assertEquals("test-mysql-password", mysqlKey.get("password"));
        assertEquals("test-mysql-username", mysqlKey.get("username"));

        verify(keyManagerClient, times(1)).getMysqlUrl(any(HttpHeaders.class));
        verify(keyManagerClient, times(1)).getMysqlPassword(any(HttpHeaders.class));
        verify(keyManagerClient, times(1)).getMysqlUsername(any(HttpHeaders.class));
    }

    @Test
    void testObjectMapper() {
        ObjectMapper objectMapper = keyManagerConfig.objectMapper();

        assertNotNull(objectMapper);
    }
}