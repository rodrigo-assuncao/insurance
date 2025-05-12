package com.insurance.infrastructure.service;

import com.insurance.application.dto.OrderStatusUpdateDto;
import com.insurance.domain.model.Order;
import com.insurance.domain.enums.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderStatusUpdateServiceTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private OrderStatusUpdateService service;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(this.service, "exchangeName", "test.exchange");
        ReflectionTestUtils.setField(this.service, "routingKey", "test.routing");
    }

    @Test
    void shouldSendStatusUpdateMessage() {
        var order = new Order();
        order.setId(UUID.randomUUID());

        var status = StatusEnum.APPROVED;

        service.send(order, status);

        ArgumentCaptor<OrderStatusUpdateDto> captor = ArgumentCaptor.forClass(OrderStatusUpdateDto.class);
        verify(rabbitTemplate).convertAndSend(eq("test.exchange"), eq("test.routing"), captor.capture());

        var sentDto = captor.getValue();
        assertNotNull(sentDto);
        assertEquals(order.getId().toString(), sentDto.getOrderId());
        assertEquals(status, sentDto.getStatus());
    }
}