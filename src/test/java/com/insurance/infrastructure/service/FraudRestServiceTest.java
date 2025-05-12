package com.insurance.infrastructure.service;

import com.insurance.application.dto.response.FraudCustomerValidationResponse;
import com.insurance.domain.model.Order;
import com.insurance.infrastructure.client.FraudRestClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static com.insurance.domain.enums.ProfileClassificationEnum.HIGH_RISK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FraudRestServiceTest {

    @Mock
    private FraudRestClient fraudRestClient;

    @InjectMocks
    private FraudRestService fraudRestService;

    @Test
    void shouldCallFraudClient() {
        var customerId = UUID.randomUUID();
        var order = new Order();
        order.setCustomerId(customerId);

        FraudCustomerValidationResponse response = new FraudCustomerValidationResponse();
        response.setClassification(HIGH_RISK);

        when(fraudRestClient.validateClientOrder(customerId.toString())).thenReturn(response);

        FraudCustomerValidationResponse result = fraudRestService.validateCustomerOrder(order);

        assertNotNull(result);
        assertEquals(HIGH_RISK, result.getClassification());
        verify(fraudRestClient).validateClientOrder(customerId.toString());
    }
}