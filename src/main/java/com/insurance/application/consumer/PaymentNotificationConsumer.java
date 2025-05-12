package com.insurance.application.consumer;

import com.insurance.application.dto.PaymentNotificationDto;
import com.insurance.domain.usecase.ProcessOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentNotificationConsumer {

    private final ProcessOrder processOrder;

    @RabbitListener(queues = "${spring.rabbitmq.topic.payment-notification.queue}")
    public void consumer(PaymentNotificationDto payload) {
        log.info("Consume Payment Notification for order {} to payment status {}", payload.getOrderId(), payload.getStatus());
        this.processOrder.updateStatusOrder(payload.getOrderId(), payload.getStatus().getOrderStatus());
    }

}
