package com.insurance.domain.usecase;

import com.insurance.application.dto.request.OrderRequest;
import com.insurance.application.dto.response.OrderResponse;
import com.insurance.application.exceptions.BadRequestException;
import com.insurance.application.mapper.OrderMapper;
import com.insurance.domain.enums.OrderStatusEnum;
import com.insurance.domain.model.History;
import com.insurance.domain.validator.customerprofile.CustomerProfileValidatorFactory;
import com.insurance.infrastructure.service.FraudRestService;
import com.insurance.infrastructure.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.insurance.domain.enums.OrderStatusEnum.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessOrder {

    private final OrderMapper orderMapper;
    private final OrderService orderService;
    private final FraudRestService fraudRestService;
    private final CustomerProfileValidatorFactory customerProfileValidator;

    public OrderResponse createOrder(OrderRequest request) {
        log.info("Start processing new order.");
        log.info("Creating new order for customer[{}].", request.getCustomerId());
        var savedOrder = this.orderService.save(
                this.orderMapper.toEntity(request).withId(UUID.randomUUID())
        );

        log.info("Order created id[{}]. customer[{}]", savedOrder.getId().toString(), savedOrder.getCustomerId());

        orderService.updateOrderStatus(savedOrder, RECEIVED);

        log.info("Send customer for fraud validation. customer[{}]", savedOrder.getCustomerId());
        var fraudResponse = this.fraudRestService.validateCustomerOrder(savedOrder);

        log.info("Validate order by Profile Classification. customer[{}]", savedOrder.getCustomerId());
        var profileValidationStatusResult = this.customerProfileValidator
                .getCustomerProfileValidatorByClassification(fraudResponse.getClassification())
                .execute(savedOrder);

        log.info("Order[{}] validated, result -> {}. customer[{}]", savedOrder.getId(), profileValidationStatusResult.name(), savedOrder.getCustomerId());

        orderService.updateOrderStatus(savedOrder, profileValidationStatusResult);

        if(profileValidationStatusResult != REJECTED) {
            orderService.updateOrderStatus(savedOrder, PENDING);
        }

        log.info("Finished processing order.");
        return this.orderMapper.toResponse(savedOrder);
    }

    public void updateStatusOrder(String orderId, OrderStatusEnum status) {
        var order = this.orderService.findById(UUID.fromString(orderId));

        if (order.getHistory().stream().anyMatch(history -> history.getStatus() == CANCELED)) {
            throw new BadRequestException("Order Canceled");
        }

        var updatedOrder = this.orderService.updateOrderStatus(order, status);

        var lastOrderStatus = updatedOrder.getHistory().stream()
                .map(History::getStatus)
                .toList();

        if (lastOrderStatus.contains(SUBSCRIPTION_ALLOWED) && lastOrderStatus.contains(PAYMENT_CONFIRMED)) {
            log.info("Order[{}] Approved", orderId);
            updatedOrder.setFinishedAt(LocalDateTime.now());
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

        log.info("Retrieve last Status for order[{}]", order);
        var lastHistoryOptional = order.lastOrderHistory();

        if (lastHistoryOptional.isPresent()) {
            var lastHistory = lastHistoryOptional.get();
            log.info("Last Status for order[{}] -> {}", order, lastHistory.getStatus());
            if (lastHistory.getStatus() == CANCELED) {
                log.info("Order[{}] already canceled.", orderId);
                throw new BadRequestException("Order already canceled");
            } else if (lastHistory.getStatus() == APPROVED || lastHistory.getStatus() == REJECTED) {
                log.info("Cancel Order[{}] not allowed. Order status is {}", orderId, lastHistory.getStatus());
                throw new BadRequestException("Cancel Order not allowed. Order status is " + StringUtils.capitalize(lastHistory.getStatus().name()));
            }
        }

        var updatedOrder = this.orderService.updateOrderStatus(order, CANCELED);

        log.info("Order[{}] canceled.", orderId);
        return this.orderMapper.toResponse(updatedOrder);
    }

}
