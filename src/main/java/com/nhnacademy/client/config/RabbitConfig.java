package com.nhnacademy.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitConfig {

    @Value("${rabbit.login.exchange.name}")
    private String loginExchangeName;
    @Value("${rabbit.login.queue.name}")
    private String loginQueueName;
    @Value("${rabbit.login.routing.key}")
    private String loginRoutingKey;

    @Value("${rabbit.register.exchange.name}")
    private String registerExchangeName;
    @Value("${rabbit.register.queue.name}")
    private String registerQueueName;
    @Value("${rabbit.register.routing.key}")
    private String registerRoutingKey;

    @Bean
    DirectExchange loginExchange() {
        return new DirectExchange(loginExchangeName);
    }

    @Bean
    Queue loginQueue() {
        return new Queue(loginQueueName);
    }

    @Bean
    Binding loginBinding(Queue loginQueue, DirectExchange loginExchange) {
        return BindingBuilder.bind(loginQueue).to(loginExchange).with(loginRoutingKey);
    }

    @Bean
    DirectExchange registerExchange() {
        return new DirectExchange(registerExchangeName);
    }

    @Bean
    Queue registerQueue() {
        return new Queue(registerQueueName);
    }

    @Bean
    Binding registerBinding(Queue registerQueue, DirectExchange registerExchange) {
        return BindingBuilder.bind(registerQueue).to(registerExchange).with(registerRoutingKey);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
