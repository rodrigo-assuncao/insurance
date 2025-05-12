package com.insurance.application.consumer;

import com.insurance.application.dto.PaymentNotificationDto;
import com.insurance.domain.model.enums.PaymentProcessEnum;
import com.insurance.domain.usecase.ProcessOrder;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RabbitListenerTest
@TestPropertySource(properties = {
        "spring.rabbitmq.topic.payment-notification.queue=test.payment.queue"
})
class PaymentNotificationConsumerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @SpyBean
    private PaymentNotificationConsumer paymentNotificationConsumer;

    @MockBean
    private ProcessOrder processOrder;

    private static final String TEST_QUEUE = "test.payment.queue";

    @Test
    void shouldConsumerRabbitMQMessage() {
        var dto = new PaymentNotificationDto();
        dto.setOrderId(UUID.randomUUID().toString());
        dto.setStatus(PaymentProcessEnum.CONFIRMED);

        rabbitTemplate.convertAndSend("", TEST_QUEUE, dto);

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() ->
                verify(paymentNotificationConsumer, times(1)).consumer(any(PaymentNotificationDto.class))
        );

        verify(processOrder).updateStatusOrder(dto.getOrderId(), dto.getStatus().getOrderStatus());
    }

}