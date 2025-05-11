package com.insurance.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.topic.status-update.exchange}")
    private String exchangeName;

    @Value("${spring.rabbitmq.topic.status-update.routing-key}")
    private String routingKey;

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Queue statusUpdateQueue(
            @Value("${spring.rabbitmq.topic.status-update.queue}")
            String queueName
    ) {
        return new Queue(queueName, true);
    }

    @Bean
    public Queue paymentNotification(
            @Value("${spring.rabbitmq.topic.payment-notification.queue}")
            String queueName
    ) {
        return new Queue(queueName, true);
    }

    @Bean
    public Queue subscriptionNotification(
            @Value("${spring.rabbitmq.topic.subscription-notification.queue}")
            String queueName
    ) {
        return new Queue(queueName, true);
    }

    @Bean
    public Binding binding(Queue statusUpdateQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(statusUpdateQueue).to(topicExchange).with(routingKey);
    }

}
