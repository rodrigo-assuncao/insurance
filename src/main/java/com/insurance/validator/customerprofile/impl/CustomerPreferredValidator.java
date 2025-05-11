package com.insurance.validator.customerprofile.impl;

import com.insurance.enums.ProfileClassificationEnum;
import com.insurance.model.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CustomerPreferredValidator extends AbstractCustomerProfileValidator {

    @Override
    public ProfileClassificationEnum getClassificationEnum() {
        return ProfileClassificationEnum.PREFERRED;
    }

    @Override
    protected boolean isCategoryValid(Order order) {
        return switch (order.getCategory()) {
            case LIFE -> order.getInsuredAmount().compareTo(new BigDecimal("800000")) >= 0;
            case AUTO, RESIDENTIAL -> order.getInsuredAmount().compareTo(new BigDecimal("450000")) >= 0;
            default -> order.getInsuredAmount().compareTo(new BigDecimal("375000")) <= 0;
        };
    }
}
