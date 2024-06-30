package com.nhnacademy.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({RabbitConfigTest.TestConfig.class, RabbitConfig.class})
class RabbitConfigTest {

    @Configuration
    static class TestConfig {
        @Bean
        public ConnectionFactory connectionFactory() {
            return new CachingConnectionFactory("localhost");
        }
    }

    @Autowired
    private ApplicationContext context;

    @Test
    void testLoginExchange() {
        DirectExchange loginExchange = context.getBean("loginExchange", DirectExchange.class);
        assertThat(loginExchange).isNotNull();
        assertThat(loginExchange.getName()).isEqualTo("code-quest.client.login.exchange");
    }

    @Test
    void testLoginQueue() {
        Queue loginQueue = context.getBean("loginQueue", Queue.class);
        assertThat(loginQueue).isNotNull();
        assertThat(loginQueue.getName()).isEqualTo("code-quest.client.login.queue");
    }

    @Test
    void testLoginBinding() {
        Binding loginBinding = context.getBean("loginBinding", Binding.class);
        assertThat(loginBinding).isNotNull();
        assertThat(loginBinding.getRoutingKey()).isEqualTo("code-quest.client.login.key");
    }

    @Test
    void testRabbitTemplate() {
        RabbitTemplate rabbitTemplate = context.getBean("rabbitTemplate", RabbitTemplate.class);
        assertThat(rabbitTemplate).isNotNull();
        assertThat(rabbitTemplate.getMessageConverter()).isInstanceOf(Jackson2JsonMessageConverter.class);
    }

    @Test
    void testObjectMapper() {
        ObjectMapper objectMapper = context.getBean("objectMapper", ObjectMapper.class);
        assertThat(objectMapper).isNotNull();
    }
}
