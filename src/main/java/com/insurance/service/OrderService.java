package com.insurance.service;

import com.insurance.model.Order;
import com.insurance.repository.mongo.OrderMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.insurance.enums.StatusEnum;

import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMongoRepository repository;
    private final OrderStatusUpdateService orderStatusUpdateService;

    public Order save(Order Order) {
        return this.repository.save(Order);
    }

    public Order findById(UUID id) {
        return this.repository.findById(id)
                .orElseThrow();
    }

    public void deleteById(UUID id) {
        this.repository.deleteById(id);
    }

    public Order updateOrderStatus(Order order, StatusEnum status) {
        if (order.getHistory() == null) {
            order.setHistory(new ArrayList<>());
        }

        order.getHistory().add(status.createHistory());

        var savedSolicitation = this.save(order);

        orderStatusUpdateService.send(savedSolicitation, status);

        return savedSolicitation;


    }

}
