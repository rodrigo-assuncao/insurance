package com.insurance.domain.validator.customerprofile.impl;

import com.insurance.domain.model.Order;
import com.insurance.domain.enums.ProfileClassificationEnum;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CustomerHighRiskValidator extends AbstractCustomerProfileValidator {

    @Override
    public ProfileClassificationEnum getClassificationEnum() {
        return ProfileClassificationEnum.HIGH_RISK;
    }

    @Override
    protected boolean isCategoryValid(Order order) {
        return switch (order.getCategory()) {
            case LIFE, RESIDENTIAL -> order.getInsuredAmount().compareTo(new BigDecimal("500000")) <= 0;
            case AUTO -> order.getInsuredAmount().compareTo(new BigDecimal("350000")) <= 0;
            default -> order.getInsuredAmount().compareTo(new BigDecimal("255000")) <= 0;
        };
    }
}
