package com.insurance.domain.validator.customerprofile.impl;

import com.insurance.domain.enums.ProfileClassificationEnum;
import com.insurance.domain.model.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CustomerNoInformationValidator extends AbstractCustomerProfileValidator {

    @Override
    public ProfileClassificationEnum getClassificationEnum() {
        return ProfileClassificationEnum.NO_INFORMATION;
    }

    @Override
    protected boolean isCategoryValid(Order order) {
        return switch (order.getCategory()) {
            case LIFE, RESIDENTIAL -> order.getInsuredAmount().compareTo(new BigDecimal("200000")) <= 0;
            case AUTO -> order.getInsuredAmount().compareTo(new BigDecimal("75000")) <= 0;
            default -> order.getInsuredAmount().compareTo(new BigDecimal("55000")) <= 0;
        };
    }
}
