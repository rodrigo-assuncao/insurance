package com.insurance.infrastructure.service;

import com.insurance.application.dto.response.FraudCustomerValidationResponse;
import com.insurance.domain.model.Order;
import com.insurance.infrastructure.client.FraudRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FraudRestService {

    private final FraudRestClient fraudRestClient;

    public FraudCustomerValidationResponse validateCustomerOrder(Order order) {
        return this.fraudRestClient.validateClientOrder(order.getCustomerId().toString());
    }

}
