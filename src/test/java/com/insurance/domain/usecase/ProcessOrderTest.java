package com.insurance.domain.usecase;

import com.insurance.application.dto.request.OrderRequest;
import com.insurance.application.dto.response.FraudCustomerValidationResponse;
import com.insurance.application.dto.response.OrderResponse;
import com.insurance.application.exceptions.BadRequest;
import com.insurance.application.mapper.OrderMapper;
import com.insurance.domain.model.History;
import com.insurance.domain.model.Order;
import com.insurance.domain.model.enums.ProfileClassificationEnum;
import com.insurance.domain.model.enums.StatusEnum;
import com.insurance.domain.validator.customerprofile.CustomerProfileValidator;
import com.insurance.domain.validator.customerprofile.CustomerProfileValidatorFactory;
import com.insurance.infrastructure.service.FraudRestService;
import com.insurance.infrastructure.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.insurance.domain.model.enums.StatusEnum.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessOrderTest {

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderService orderService;

    @Mock
    private FraudRestService fraudRestService;

    @Mock
    private CustomerProfileValidatorFactory validatorFactory;

    @InjectMocks
    private ProcessOrder processOrder;

    @Test
    void testCreateOrder_orderValidated() {
        this.testCreateOrder(VALIDATED, 3);
    }

    @Test
    void testCreateOrder_orderRejected() {
        this.testCreateOrder(REJECTED, 2);
    }

    @Test
    void testUpdateStatusOrder_orderNotificationUpdate() {
        var findOrder = new Order();
        findOrder.setId(UUID.randomUUID());
        findOrder.setHistory(List.of(
                mockHistory(RECEIVED),
                mockHistory(VALIDATED),
                mockHistory(PENDING)
        ));

        when(this.orderService.findById(eq(findOrder.getId()))).thenReturn(findOrder);
        when(orderService.updateOrderStatus(any(), any())).thenReturn(findOrder);


        processOrder.updateStatusOrder(findOrder.getId().toString(), SUBSCRIPTION_ALLOWED);

        verify(orderService, times(1)).findById(eq(findOrder.getId()));
        verify(orderService, times(1)).updateOrderStatus(eq(findOrder), any());
    }

    @Test
    void testUpdateStatusOrder_orderApproved() {
        var findOrder = new Order();
        findOrder.setId(UUID.randomUUID());
        findOrder.setHistory(new ArrayList<>());
        findOrder.getHistory().add(mockHistory(RECEIVED));
        findOrder.getHistory().add(mockHistory(VALIDATED));
        findOrder.getHistory().add(mockHistory(PENDING));
        findOrder.getHistory().add(mockHistory(PAYMENT_CONFIRMED));

        var updatedOrderWithSubscriptionAllowedStatus = findOrder.withHistory(findOrder.getHistory());
        updatedOrderWithSubscriptionAllowedStatus.getHistory().add(mockHistory(SUBSCRIPTION_ALLOWED));

        when(this.orderService.findById(eq(findOrder.getId()))).thenReturn(findOrder);
        when(orderService.updateOrderStatus(any(), eq(SUBSCRIPTION_ALLOWED))).thenReturn(updatedOrderWithSubscriptionAllowedStatus);

        processOrder.updateStatusOrder(findOrder.getId().toString(), SUBSCRIPTION_ALLOWED);

        verify(orderService, times(1)).findById(eq(findOrder.getId()));
        verify(orderService, times(2)).updateOrderStatus(eq(findOrder), any());
    }

    @Test
    void testUpdateStatusOrder_orderCanceled() {
        var findOrder = new Order();
        findOrder.setId(UUID.randomUUID());
        findOrder.setHistory(List.of(
                mockHistory(RECEIVED),
                mockHistory(VALIDATED),
                mockHistory(PENDING),
                mockHistory(PAYMENT_CONFIRMED),
                mockHistory(CANCELED)
        ));

        when(this.orderService.findById(eq(findOrder.getId()))).thenReturn(findOrder);

        assertThrows(BadRequest.class, () -> processOrder.updateStatusOrder(findOrder.getId().toString(), SUBSCRIPTION_ALLOWED));
    }

    @Test
    void testFindOrderByOrderId_success() {
        var orderId = UUID.randomUUID();
        Order order = new Order();
        order.setId(orderId);

        OrderResponse expectedResponse = new OrderResponse();
        expectedResponse.setId(orderId);

        when(orderService.findById(orderId)).thenReturn(order);
        when(orderMapper.toResponse(order)).thenReturn(expectedResponse);

        OrderResponse result = processOrder.findOrderByOrderId(orderId.toString());

        assertNotNull(result);
        assertEquals(orderId, result.getId());
        verify(orderService).findById(orderId);
        verify(orderMapper).toResponse(order);
    }

    @Test
    void testCancelOrder_success() {
        var findOrder = new Order();
        findOrder.setId(UUID.randomUUID());
        findOrder.setHistory(List.of(
                mockHistory(RECEIVED),
                mockHistory(VALIDATED),
                mockHistory(PENDING),
                mockHistory(PAYMENT_CONFIRMED)
        ));

        when(this.orderService.findById(eq(findOrder.getId()))).thenReturn(findOrder);
        when(orderService.updateOrderStatus(any(), any())).thenReturn(findOrder);

        processOrder.cancelOrder(findOrder.getId().toString());

        verify(orderService, times(1)).findById(eq(findOrder.getId()));
        verify(orderService, times(1)).updateOrderStatus(eq(findOrder), any());
    }

    @Test
    void testCancelOrder_alreadyCanceled() {
        testCancelOrderLastStatus(CANCELED);
    }

    @Test
    void testCancelOrder_approvedLastStatus() {
        testCancelOrderLastStatus(APPROVED);
    }

    @Test
    void testCancelOrder_rejectedLastStatus() {
        testCancelOrderLastStatus(REJECTED);
    }

    private void testCancelOrderLastStatus(StatusEnum status) {
        var findOrder = new Order();
        findOrder.setId(UUID.randomUUID());
        findOrder.setHistory(List.of(
                mockHistory(status)
        ));

        when(this.orderService.findById(eq(findOrder.getId()))).thenReturn(findOrder);

        assertThrows(BadRequest.class, () -> processOrder.cancelOrder(findOrder.getId().toString()));
    }

    private void testCreateOrder(StatusEnum finalStatus, int timesUpdatedStatusCalled) {
        var customerId = UUID.randomUUID();
        OrderRequest request = new OrderRequest();
        request.setCustomerId(customerId);

        var orderEntity = new Order();
        orderEntity.setId(UUID.randomUUID());
        orderEntity.setCustomerId(customerId);

        when(orderMapper.toEntity(any())).thenReturn(orderEntity);
        when(orderService.save(any())).thenReturn(orderEntity);
        when(fraudRestService.validateCustomerOrder(any())).thenReturn(mockFraudResponse(ProfileClassificationEnum.HIGH_RISK));

        var validator = mock(CustomerProfileValidator.class);
        when(validatorFactory.getCustomerProfileValidatorByClassification(any())).thenReturn(validator);
        when(validator.execute(any())).thenReturn(finalStatus);

        when(orderService.updateOrderStatus(any(), any())).thenReturn(orderEntity);
        when(orderMapper.toResponse(any())).thenReturn(new OrderResponse());

        OrderResponse response = processOrder.createOrder(request);

        assertNotNull(response);
        verify(orderService, times(timesUpdatedStatusCalled)).updateOrderStatus(eq(orderEntity), any());
    }

    private FraudCustomerValidationResponse mockFraudResponse(ProfileClassificationEnum classification) {
        FraudCustomerValidationResponse response = new FraudCustomerValidationResponse();
        response.setClassification(classification);
        return response;
    }

    private History mockHistory(StatusEnum status) {
        return History.builder()
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
