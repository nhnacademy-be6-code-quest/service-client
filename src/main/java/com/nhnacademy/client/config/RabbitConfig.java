package com.nhnacademy.client.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RabbitConfig {
    private static final String DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
    private static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    private final Map<String, String> rabbitKey;

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
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitKey.get("host"), Integer.parseInt(rabbitKey.get("port")));
        connectionFactory.setUsername(rabbitKey.get("username"));
        connectionFactory.setPassword(rabbitKey.get("password"));
        return connectionFactory;
    }


    @Bean
    public DirectExchange loginExchange() {
        return new DirectExchange(loginExchangeName);
    }
    @Bean
    public Queue loginDlqQueue() {
        return new Queue(loginDlqQueueName);
    }
    @Bean
    public Binding loginDlqBinding(Queue loginDlqQueue, DirectExchange loginExchange) {
        return BindingBuilder.bind(loginDlqQueue).to(loginExchange).with(loginDlqRoutingKey);
    }
    @Bean
    public Queue loginQueue() {
        return QueueBuilder.durable(loginQueueName)
                .withArgument(DEAD_LETTER_EXCHANGE, loginExchangeName)
                .withArgument(DEAD_LETTER_ROUTING_KEY, loginDlqRoutingKey)
                .build();
    }
    @Bean
    public Binding loginBinding(Queue loginQueue, DirectExchange loginExchange) {
        return BindingBuilder.bind(loginQueue).to(loginExchange).with(loginRoutingKey);
    }


    @Bean
    public DirectExchange registerExchange() {
        return new DirectExchange(registerExchangeName);
    }
    @Bean
    public Queue registerQueue() {
        return QueueBuilder.durable(registerQueueName)
                .withArgument(DEAD_LETTER_EXCHANGE, registerExchangeName)
                .withArgument(DEAD_LETTER_ROUTING_KEY, registerDlqRoutingKey)
                .build();
    }
    @Bean
    public Binding registerBinding(Queue registerQueue, DirectExchange registerExchange) {
        return BindingBuilder.bind(registerQueue).to(registerExchange).with(registerRoutingKey);
    }


    @Bean
    public DirectExchange registerPointExchange() {
        return new DirectExchange(registerPointExchangeName);
    }
    @Bean
    public Queue registerPointQueue() {
        return QueueBuilder.durable(registerPointQueueName)
                .withArgument(DEAD_LETTER_EXCHANGE, registerPointExchangeName)
                .withArgument(DEAD_LETTER_ROUTING_KEY, registerPointDlqRoutingKey)
                .build();
    }
    @Bean
    public Binding registerPointBinding(Queue registerPointQueue, DirectExchange registerPointExchange) {
        return BindingBuilder.bind(registerPointQueue).to(registerPointExchange).with(registerPointRoutingKey);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}
