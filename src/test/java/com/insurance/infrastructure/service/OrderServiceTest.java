package com.insurance.infrastructure.service;

import com.insurance.application.exceptions.BadRequest;
import com.insurance.domain.model.Order;
import com.insurance.domain.enums.StatusEnum;
import com.insurance.infrastructure.mongo.OrderMongoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderMongoRepository repository;

    @Mock
    private OrderStatusUpdateService orderStatusUpdateService;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldSaveOrder() {
        var order = new Order();
        order.setId(UUID.randomUUID());
        order.setCustomerId(UUID.randomUUID());

        when(repository.save(order)).thenReturn(order);

        var saved = orderService.save(order);

        assertEquals(order, saved);
        verify(repository).save(order);
    }

    @Test
    void shouldFindOrderById() {
        var order = new Order();
        order.setId(UUID.randomUUID());
        order.setCustomerId(UUID.randomUUID());

        var id = order.getId();
        when(repository.findById(id)).thenReturn(Optional.of(order));

        var found = orderService.findById(id);

        assertEquals(order, found);
        verify(repository).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenOrderNotFound() {
        var order = new Order();
        order.setId(UUID.randomUUID());
        order.setCustomerId(UUID.randomUUID());

        var id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BadRequest.class, () -> orderService.findById(id));
    }

    @Test
    void shouldUpdateOrderStatusAndSendNotification() {
        var order = new Order();
        order.setId(UUID.randomUUID());
        order.setCustomerId(UUID.randomUUID());

        when(repository.save(order)).thenReturn(order);

        var updated = orderService.updateOrderStatus(order, StatusEnum.APPROVED);

        assertNotNull(updated.getHistory());
        assertEquals(1, updated.getHistory().size());

        verify(repository).save(order);
        verify(orderStatusUpdateService).send(order, StatusEnum.APPROVED);
    }

}