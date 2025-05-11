package com.insurance.usecase;

import com.insurance.dto.request.OrderRequest;
import com.insurance.dto.response.OrderResponse;
import com.insurance.enums.StatusEnum;
import com.insurance.mapper.OrderMapper;
import com.insurance.model.History;
import com.insurance.service.FraudRestService;
import com.insurance.service.OrderService;
import com.insurance.validator.customerprofile.CustomerProfileValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.insurance.enums.StatusEnum.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessOrder {

    private final OrderMapper orderMapper;
    private final OrderService orderService;
    private final FraudRestService fraudRestService;
    private final CustomerProfileValidatorFactory customerProfileValidator;

    public OrderResponse createOrder(OrderRequest request) {
        var savedOrder = this.orderService.save(
                this.orderMapper.toEntity(request).withId(UUID.randomUUID())
        );

        orderService.updateOrderStatus(savedOrder, RECEIVED);

        var fraudResponse = this.fraudRestService.validateCustomerOrder(savedOrder);

        var profileValidationStatusResult = this.customerProfileValidator
                .getCustomerProfileValidatorByClassification(fraudResponse.getClassification())
                .execute(savedOrder);

        orderService.updateOrderStatus(savedOrder, profileValidationStatusResult);

        if(profileValidationStatusResult != REJECTED) {
            orderService.updateOrderStatus(savedOrder, PENDING);
        }

        return this.orderMapper.toResponse(savedOrder);
    }

    public void updateStatusOrder(String orderId, StatusEnum status) {
        var order = this.orderService.findById(UUID.fromString(orderId));

        if (order.getHistory().stream().anyMatch(history -> history.getStatus() == CANCELED)) {
            throw new RuntimeException("Order Canceled");
        }

        var updatedOrder = this.orderService.updateOrderStatus(order, status);

        var lastOrderStatus = updatedOrder.getHistory().stream()
                .map(History::getStatus)
                .toList();

        if (lastOrderStatus.contains(SUBSCRIPTION_ALLOWED) && lastOrderStatus.contains(PAYMENT_CONFIRMED)) {
            this.orderService.updateOrderStatus(updatedOrder, APPROVED);
        }
    }

    public OrderResponse findOrderByOrderId(String orderId) {
        return this.orderMapper.toResponse(
                this.orderService.findById(UUID.fromString(orderId))
        );
    }

    public OrderResponse cancelOrder(String orderId) {
        var order = this.orderService.findById(UUID.fromString(orderId));

        var lastHistoryOptional = order.lastOrderHistory();

        if (lastHistoryOptional.isPresent()) {
            var lastHistory = lastHistoryOptional.get();
            if (lastHistory.getStatus() == CANCELED) {
                throw new RuntimeException("Order already canceled");
            } else if (lastHistory.getStatus() == APPROVED || lastHistory.getStatus() == REJECTED) {
                throw new RuntimeException("Cancel Order not allowed. Order status is " + StringUtils.capitalize(lastHistory.getStatus().name()));
            }
        }

        var updatedOrder = this.orderService.updateOrderStatus(order, CANCELED);

        return this.orderMapper.toResponse(updatedOrder);
    }

}
