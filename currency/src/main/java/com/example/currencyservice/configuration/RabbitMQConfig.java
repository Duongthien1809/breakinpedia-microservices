package com.example.currencyservice.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String rabbitMQHost;

    @Value("${spring.rabbitmq.username}")
    private String rabbitMQUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitMQPassword;

    // Connection Factory Bean
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitMQHost);
        connectionFactory.setUsername(rabbitMQUsername);
        connectionFactory.setPassword(rabbitMQPassword);
        return connectionFactory;
    }

    // Rabbit Template Bean
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    // JSON Message Converter Bean
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Rabbit Listener Container Factory Bean
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory, SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    // Queue Beans
    @Bean
    public Queue currencyResponseQueue() {
        return new Queue("currency-response-queue");
    }

    @Bean
    public Queue orderResponseQueue() {
        return new Queue("order-response-queue");
    }

    @Bean
    public Queue paymentResponseQueue() {
        return new Queue("payment-response-queue");
    }

    // Exchange Beans
    @Bean
    public TopicExchange currencyExchange() {
        return new TopicExchange("currency-exchange");
    }

    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange("payment-exchange");
    }

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange("order-exchange");
    }

    // Binding Beans
    @Bean
    public Binding currencyRequestBinding() {
        return BindingBuilder.bind(currencyResponseQueue()).to(currencyExchange()).with("CurrencyRoutingKey");
    }

    @Bean
    public Binding paymentResponseBinding() {
        return BindingBuilder.bind(paymentResponseQueue()).to(paymentExchange()).with("PaymentRoutingKey");
    }

    @Bean
    public Binding orderResponseBinding() {
        return BindingBuilder.bind(orderResponseQueue()).to(orderExchange()).with("OrderRoutingKey");
    }
}

