package com.insurance.consumer;

import com.insurance.dto.SubscriptionNotificationDto;
import com.insurance.usecase.ProcessOrder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionNotificationConsumer {

    private ProcessOrder processOrder;

    @RabbitListener(queues = "${spring.rabbitmq.topic.subscription-notification.queue}")
    public void consumer(SubscriptionNotificationDto payload) {
        this.processOrder.updateStatusOrder(payload.getOrderId(), payload.getStatus().getOrderStatus());
    }

}
