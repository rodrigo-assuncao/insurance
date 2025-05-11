package com.insurance.consumer;

import com.insurance.dto.PaymentNotificationDto;
import com.insurance.usecase.ProcessOrder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentNotificationConsumer {

    private ProcessOrder processOrder;

    @RabbitListener(queues = "${spring.rabbitmq.topic.payment-notification.queue}")
    public void consumer(PaymentNotificationDto payload) {
        this.processOrder.updateStatusOrder(payload.getOrderId(), payload.getStatus());
    }

}
