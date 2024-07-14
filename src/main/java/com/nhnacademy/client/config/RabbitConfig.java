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
    private static final String DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
    private static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    @Value("${rabbit.login.exchange.name}")
    private String loginExchangeName;
    @Value("${rabbit.login.queue.name}")
    private String loginQueueName;
    @Value("${rabbit.login.routing.key}")
    private String loginRoutingKey;
    @Value("${rabbit.login.dlq.queue.name}")
    private String loginDlqQueueName;
    @Value("${rabbit.login.dlq.routing.key}")
    private String loginDlqRoutingKey;

    @Value("${rabbit.register.exchange.name}")
    private String registerExchangeName;
    @Value("${rabbit.register.queue.name}")
    private String registerQueueName;
    @Value("${rabbit.register.routing.key}")
    private String registerRoutingKey;
    @Value("${rabbit.register.dlq.routing.key}")
    private String registerDlqRoutingKey;

    @Value("${rabbit.point.exchange.name}")
    private String registerPointExchangeName;
    @Value("${rabbit.point.queue.name}")
    private String registerPointQueueName;
    @Value("${rabbit.point.routing.key}")
    private String registerPointRoutingKey;
    @Value("${rabbit.point.dlq.routing.key}")
    private String registerPointDlqRoutingKey;


    @Bean
    DirectExchange loginExchange() {
        return new DirectExchange(loginExchangeName);
    }
    @Bean
    Queue loginDlqQueue() {
        return new Queue(loginDlqQueueName);
    }
    @Bean
    Binding loginDlqBinding(Queue loginDlqQueue, DirectExchange loginExchange) {
        return BindingBuilder.bind(loginDlqQueue).to(loginExchange).with(loginDlqRoutingKey);
    }
    @Bean
    Queue loginQueue() {
        return QueueBuilder.durable(loginQueueName)
                .withArgument(DEAD_LETTER_EXCHANGE, loginExchangeName)
                .withArgument(DEAD_LETTER_ROUTING_KEY, loginDlqRoutingKey)
                .build();
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
        return QueueBuilder.durable(registerQueueName)
                .withArgument(DEAD_LETTER_EXCHANGE, registerExchangeName)
                .withArgument(DEAD_LETTER_ROUTING_KEY, registerDlqRoutingKey)
                .build();
    }
    @Bean
    Binding registerBinding(Queue registerQueue, DirectExchange registerExchange) {
        return BindingBuilder.bind(registerQueue).to(registerExchange).with(registerRoutingKey);
    }


    @Bean
    DirectExchange registerPointExchange() {
        return new DirectExchange(registerPointExchangeName);
    }
    @Bean
    Queue registerPointQueue() {
        return QueueBuilder.durable(registerPointQueueName)
                .withArgument(DEAD_LETTER_EXCHANGE, registerPointExchangeName)
                .withArgument(DEAD_LETTER_ROUTING_KEY, registerPointDlqRoutingKey)
                .build();
    }
    @Bean
    Binding registerPointBinding(Queue registerPointQueue, DirectExchange registerPointExchange) {
        return BindingBuilder.bind(registerPointQueue).to(registerPointExchange).with(registerPointRoutingKey);
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
