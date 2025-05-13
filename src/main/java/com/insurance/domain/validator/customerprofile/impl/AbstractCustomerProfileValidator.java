package com.insurance.domain.validator.customerprofile.impl;

import com.insurance.domain.enums.StatusEnum;
import com.insurance.domain.model.Order;
import com.insurance.domain.validator.customerprofile.CustomerProfileValidator;
import lombok.extern.slf4j.Slf4j;

import static com.insurance.domain.enums.StatusEnum.REJECTED;
import static com.insurance.domain.enums.StatusEnum.VALIDATED;

@Slf4j
public abstract class AbstractCustomerProfileValidator implements CustomerProfileValidator {

    @Override
    public StatusEnum execute(Order order) {
        log.info("Validate order[{}] for Profile Classification {}. customer[{}]", order.getId(), this.getClassificationEnum().name(), order.getCustomerId());
        return isCategoryValid(order) ? VALIDATED : REJECTED;
    }

    protected abstract boolean isCategoryValid(Order order);

}
