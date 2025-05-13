package com.insurance.infrastructure.service;

import com.insurance.application.exceptions.NotFoundException;
import com.insurance.domain.enums.StatusEnum;
import com.insurance.domain.model.Order;
import com.insurance.infrastructure.mongo.OrderMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMongoRepository repository;
    private final OrderStatusUpdateService orderStatusUpdateService;

    public Order save(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        return this.repository.save(order);
    }

    public Order findById(UUID id) {
        log.info("Find order[{}]", id.toString());
        return this.repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }

    public Order updateOrderStatus(Order order, StatusEnum status) {
        log.info("Update Order[{}] to status {}. customer[{}]", order.getId().toString(), status.name(), order.getCustomerId());
        if (order.getHistory() == null) {
            order.setHistory(new ArrayList<>());
        }

        order.getHistory().add(status.createHistory());

        var savedSolicitation = this.save(order);

        orderStatusUpdateService.send(savedSolicitation, status);

        return savedSolicitation;


    }

}
