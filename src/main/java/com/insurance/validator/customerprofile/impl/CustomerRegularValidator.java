package com.insurance.validator.customerprofile.impl;

import com.insurance.enums.ProfileClassificationEnum;
import com.insurance.model.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CustomerRegularValidator extends AbstractCustomerProfileValidator {

    @Override
    public ProfileClassificationEnum getClassificationEnum() {
        return ProfileClassificationEnum.REGULAR;
    }

    @Override
    protected boolean isCategoryValid(Order order) {
        return switch (order.getCategory()) {
            case AUTO -> order.getInsuredAmount().compareTo(new BigDecimal("250000")) <= 0;
            case RESIDENTIAL -> order.getInsuredAmount().compareTo(new BigDecimal("150000")) <= 0;
            default -> order.getInsuredAmount().compareTo(new BigDecimal("125000")) <= 0;
        };
    }
}
