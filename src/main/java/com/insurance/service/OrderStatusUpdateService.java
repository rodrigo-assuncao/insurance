package com.insurance.service;

import com.insurance.dto.OrderStatusUpdateDto;
import com.insurance.enums.StatusEnum;
import com.insurance.model.Order;
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

    public void send(Order order, StatusEnum status) {

        var solicitationStatusUpdateDto = OrderStatusUpdateDto.builder()
                .solicitationId(order.getId().toString())
                .status(status)
                .build();

        this.rabbitTemplate.convertAndSend(
                exchangeName,
                routingKey,
                solicitationStatusUpdateDto
        );
    }

}
