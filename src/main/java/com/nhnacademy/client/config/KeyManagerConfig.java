package com.nhnacademy.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.client.client.KeyManagerClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KeyManagerConfig {
    private static final String PASSWORD = "password";

    private final KeyManagerClient keyManagerClient;

    @Value("${user.access.key.id}")
    private String accessKeyId;
    @Value("${secret.access.key}")
    private String accessKeySecret;

    @Bean
    public Map<String, String> redisKey() {
        String redisHost = getKey(keyManagerClient.getRedisHost(getAccessHeaders()));
        log.info("Redis Host Key: {}", redisHost);
        String redisPassword = getKey(keyManagerClient.getRedisPassword(getAccessHeaders()));
        log.info("Redis Password Key: {}", redisPassword);
        String redisPort = getKey(keyManagerClient.getRedisPort(getAccessHeaders()));
        log.info("Redis Port Key: {}", redisPort);
        String redisDb = getKey(keyManagerClient.getRedisDb(getAccessHeaders()));
        log.info("Redis Db Key: {}", redisDb);
        return Map.of("host", redisHost,
                PASSWORD, redisPassword,
                "port", redisPort,
                "db", redisDb);
    }

    @Bean
    public Map<String, String> rabbitKey() {
        String rabbitmqHost = getKey(keyManagerClient.getRabbitmqHost(getAccessHeaders()));
        log.info("Rabbitmq Host Key: {}", rabbitmqHost);
        String rabbitmqPassword = getKey(keyManagerClient.getRabbitmqPassword(getAccessHeaders()));
        log.info("Rabbitmq Password Key: {}", rabbitmqPassword);
        String rabbitmqUsername = getKey(keyManagerClient.getRabbitmqUsername(getAccessHeaders()));
        log.info("Rabbitmq Username: {}", rabbitmqUsername);
        String rabbitmqPort = getKey(keyManagerClient.getRabbitmqPort(getAccessHeaders()));
        log.info("Rabbitmq Port Key: {}", rabbitmqPort);
        return Map.of("host", rabbitmqHost,
                PASSWORD, rabbitmqPassword,
                "username", rabbitmqUsername,
                "port", rabbitmqPort);
    }

    @Bean
    public Map<String, String> mysqlKey() {
        String mysqlUrl = getKey(keyManagerClient.getMysqlUrl(getAccessHeaders()));
        log.info("Mysql Url Key: {}", mysqlUrl);
        String mysqlPassword = getKey(keyManagerClient.getMysqlPassword(getAccessHeaders()));
        log.info("Mysql Password Key: {}", mysqlPassword);
        String mysqlUsername = getKey(keyManagerClient.getMysqlUsername(getAccessHeaders()));
        log.info("Mysql Username: {}", mysqlUsername);
        return Map.of("url", mysqlUrl,
                PASSWORD, mysqlPassword,
                "username", mysqlUsername);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    private HttpHeaders getAccessHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-TC-AUTHENTICATION-ID", accessKeyId);
        headers.add("X-TC-AUTHENTICATION-SECRET", accessKeySecret);
        return headers;
    }

    private String getKey(JSONObject jsonObject) {
        Map<String, Object> responseMap = jsonObject;
        Map<String, Object> bodyMap = (Map<String, Object>) responseMap.get("body");
        return (String) bodyMap.get("secret");
    }
}
