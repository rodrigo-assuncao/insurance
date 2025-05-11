package com.insurance.validator.customerprofile.impl;

import com.insurance.enums.StatusEnum;
import com.insurance.model.Order;
import com.insurance.validator.customerprofile.CustomerProfileValidator;

import static com.insurance.enums.StatusEnum.*;

public abstract class AbstractCustomerProfileValidator implements CustomerProfileValidator {

    @Override
    public StatusEnum execute(Order order) {
        return isCategoryValid(order) ? VALIDATED : REJECTED;
    }

    protected abstract boolean isCategoryValid(Order order);

}
