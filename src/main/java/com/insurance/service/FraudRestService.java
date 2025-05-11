package com.insurance.service;

import com.insurance.client.FraudRestClient;
import com.insurance.dto.response.FraudCustomerValidationResponse;
import com.insurance.model.Order;
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
