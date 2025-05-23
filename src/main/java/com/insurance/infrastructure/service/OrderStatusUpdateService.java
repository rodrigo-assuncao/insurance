package com.insurance.infrastructure.service;

import com.insurance.application.dto.OrderStatusUpdateDto;
import com.insurance.domain.enums.OrderStatusEnum;
import com.insurance.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderStatusUpdateService {

    @Value("${spring.rabbitmq.topic.status-update.exchange}")
    private String exchangeName;

    @Value("${spring.rabbitmq.topic.status-update.routing-key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public void send(Order order, OrderStatusEnum status) {

        var solicitationStatusUpdateDto = OrderStatusUpdateDto.builder()
                .orderId(order.getId().toString())
                .status(status)
                .build();

        this.rabbitTemplate.convertAndSend(
                this.exchangeName,
                this.routingKey,
                solicitationStatusUpdateDto
        );
    }

}
