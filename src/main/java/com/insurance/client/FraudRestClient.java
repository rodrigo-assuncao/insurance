package com.insurance.client;

import com.insurance.dto.response.FraudCustomerValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "fraud-rest-service", url = "${spring.client.fraud-rest-service.url}")
public interface FraudRestClient {

    @PostMapping("/customer/{customerId}")
    FraudCustomerValidationResponse validateClientOrder(
            @PathVariable("customerId") String customerId
    );

}
