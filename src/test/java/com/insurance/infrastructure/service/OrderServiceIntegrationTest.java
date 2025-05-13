package com.insurance.infrastructure.service;

import com.insurance.domain.enums.OrderStatusEnum;
import com.insurance.domain.model.Order;
import com.insurance.infrastructure.mongo.OrderMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@ActiveProfiles("test")
@Import(OrderService.class)
class OrderServiceIntegrationTest {

    @Autowired
    private OrderMongoRepository orderMongoRepository;

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderStatusUpdateService orderStatusUpdateService;

    private Order order;

    @BeforeEach
    void setUp() {
        orderMongoRepository.deleteAll();

        order = new Order();
        order.setId(UUID.randomUUID());
        order.setCustomerId(UUID.randomUUID());
    }

    @Test
    void shouldSaveOrderInMongo() {
        Order saved = orderService.save(order);

        assertNotNull(saved.getId());
        assertEquals(this.order.getCustomerId(), saved.getCustomerId());
        assertNotNull(saved.getCreatedAt());

        Order found = orderMongoRepository.findById(saved.getId()).orElse(null);
        assertNotNull(found);
    }

    @Test
    void shouldUpdateOrderStatusWithHistory() {
        orderService.save(order);
        Order updated = orderService.updateOrderStatus(order, OrderStatusEnum.APPROVED);

        assertNotNull(updated.getHistory());
        assertEquals(1, updated.getHistory().size());
        assertEquals(OrderStatusEnum.APPROVED, updated.getHistory().get(0).getStatus());
    }
}

