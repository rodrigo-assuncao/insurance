package com.insurance.application.consumer;

import com.insurance.application.dto.SubscriptionNotificationDto;
import com.insurance.application.exceptions.BusinessException;
import com.insurance.domain.usecase.ProcessOrder;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscriptionNotificationConsumer {

    private final ProcessOrder processOrder;

    @RabbitListener(queues = "${spring.rabbitmq.topic.subscription-notification.queue}", ackMode = "MANUAL")
    public void consumer(SubscriptionNotificationDto payload, Message message, Channel channel) throws IOException {
        try {
            log.info("Consume Subscription Notification for order {} to payment status {}", payload.getOrderId(), payload.getStatus());
            this.processOrder.updateStatusOrder(payload.getOrderId(), payload.getStatus().getOrderStatus());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (BusinessException exception) {
            log.error("Message process error -> {}", exception.getMessage());
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }



}
