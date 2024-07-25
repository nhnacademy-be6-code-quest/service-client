package com.nhnacademy.client.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RabbitConfigTest {

    private RabbitConfig rabbitConfig;

    @BeforeEach
    void setUp() {
        rabbitConfig = new RabbitConfig(Map.of(
                "host", "localhost",
                "port", "5672",
                "username", "guest",
                "password", "guest")
        );

        ReflectionTestUtils.setField(rabbitConfig, "loginExchangeName", "login.exchange");
        ReflectionTestUtils.setField(rabbitConfig, "loginQueueName", "login.queue");
        ReflectionTestUtils.setField(rabbitConfig, "loginRoutingKey", "login.routing.key");
        ReflectionTestUtils.setField(rabbitConfig, "loginDlqQueueName", "login.dlq.queue");
        ReflectionTestUtils.setField(rabbitConfig, "loginDlqRoutingKey", "login.dlq.routing.key");

        ReflectionTestUtils.setField(rabbitConfig, "registerExchangeName", "register.exchange");
        ReflectionTestUtils.setField(rabbitConfig, "registerQueueName", "register.queue");
        ReflectionTestUtils.setField(rabbitConfig, "registerRoutingKey", "register.routing.key");
        ReflectionTestUtils.setField(rabbitConfig, "registerDlqRoutingKey", "register.dlq.routing.key");

        ReflectionTestUtils.setField(rabbitConfig, "registerPointExchangeName", "register.point.exchange");
        ReflectionTestUtils.setField(rabbitConfig, "registerPointQueueName", "register.point.queue");
        ReflectionTestUtils.setField(rabbitConfig, "registerPointRoutingKey", "register.point.routing.key");
        ReflectionTestUtils.setField(rabbitConfig, "registerPointDlqRoutingKey", "register.point.dlq.routing.key");
    }

    @Test
    void testConnectionFactory() {
        ConnectionFactory connectionFactory = rabbitConfig.connectionFactory();
        assertNotNull(connectionFactory);
    }

    @Test
    void testLoginExchange() {
        DirectExchange exchange = rabbitConfig.loginExchange();
        assertNotNull(exchange);
        assertEquals("login.exchange", exchange.getName());
    }

    @Test
    void testLoginDlqQueue() {
        Queue queue = rabbitConfig.loginDlqQueue();
        assertNotNull(queue);
        assertEquals("login.dlq.queue", queue.getName());
    }

    @Test
    void testLoginQueue() {
        Queue queue = rabbitConfig.loginQueue();
        assertNotNull(queue);
        assertEquals("login.queue", queue.getName());
        assertEquals("login.exchange", queue.getArguments().get("x-dead-letter-exchange"));
        assertEquals("login.dlq.routing.key", queue.getArguments().get("x-dead-letter-routing-key"));
    }

    @Test
    void testRegisterExchange() {
        DirectExchange exchange = rabbitConfig.registerExchange();
        assertNotNull(exchange);
        assertEquals("register.exchange", exchange.getName());
    }

    @Test
    void testRegisterQueue() {
        Queue queue = rabbitConfig.registerQueue();
        assertNotNull(queue);
        assertEquals("register.queue", queue.getName());
        assertEquals("register.exchange", queue.getArguments().get("x-dead-letter-exchange"));
        assertEquals("register.dlq.routing.key", queue.getArguments().get("x-dead-letter-routing-key"));
    }

    @Test
    void testRegisterPointExchange() {
        DirectExchange exchange = rabbitConfig.registerPointExchange();
        assertNotNull(exchange);
        assertEquals("register.point.exchange", exchange.getName());
    }

    @Test
    void testRegisterPointQueue() {
        Queue queue = rabbitConfig.registerPointQueue();
        assertNotNull(queue);
        assertEquals("register.point.queue", queue.getName());
        assertEquals("register.point.exchange", queue.getArguments().get("x-dead-letter-exchange"));
        assertEquals("register.point.dlq.routing.key", queue.getArguments().get("x-dead-letter-routing-key"));
    }

    @Test
    void testRabbitTemplate() {
        ConnectionFactory connectionFactory = rabbitConfig.connectionFactory();
        RabbitTemplate rabbitTemplate = rabbitConfig.rabbitTemplate(connectionFactory);
        assertNotNull(rabbitTemplate);
        assertTrue(rabbitTemplate.getMessageConverter() instanceof Jackson2JsonMessageConverter);
    }
}