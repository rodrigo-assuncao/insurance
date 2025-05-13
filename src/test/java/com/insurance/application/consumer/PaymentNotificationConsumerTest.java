package com.insurance.application.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.application.consumer.PaymentNotificationConsumer;
import com.insurance.application.dto.PaymentNotificationDto;
import com.insurance.application.exceptions.BusinessException;
import com.insurance.domain.enums.PaymentProcessStatusEnum;
import com.insurance.domain.usecase.ProcessOrder;
import com.rabbitmq.client.Channel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TEST_QUEUE = "test.payment.queue";

    @Test
    void shouldConsumerRabbitMQMessage() throws IOException {
        var dto = new PaymentNotificationDto();
        dto.setOrderId(UUID.randomUUID().toString());
        dto.setStatus(PaymentProcessStatusEnum.CONFIRMED);

        String json = objectMapper.writeValueAsString(dto);
        byte[] body = json.getBytes(StandardCharsets.UTF_8);

        MessageProperties props = new MessageProperties();
        props.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        props.setDeliveryTag(1L);
        Message message = new Message(body, props);

        Channel channel = mock(Channel.class);

        paymentNotificationConsumer.consumer(dto, message, channel);

        verify(processOrder).updateStatusOrder(dto.getOrderId(), dto.getStatus().getOrderStatus());
        verify(channel).basicAck(1L, false);
    }

    @Test
    void shouldRejectMessageWhenExceptionOccurs() throws Exception {
        var dto = new PaymentNotificationDto();
        dto.setOrderId(UUID.randomUUID().toString());
        dto.setStatus(PaymentProcessStatusEnum.CONFIRMED);

        String json = objectMapper.writeValueAsString(dto);
        byte[] body = json.getBytes(StandardCharsets.UTF_8);

        MessageProperties props = new MessageProperties();
        props.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        props.setDeliveryTag(42L);
        Message message = new Message(body, props);

        Channel channel = mock(Channel.class);

        doThrow(new BusinessException("Erro simulado")).when(processOrder)
                .updateStatusOrder(anyString(), any());

        paymentNotificationConsumer.consumer(dto, message, channel);

        verify(processOrder).updateStatusOrder(eq(dto.getOrderId()), any());
        verify(channel).basicReject(42L, false);
    }

}