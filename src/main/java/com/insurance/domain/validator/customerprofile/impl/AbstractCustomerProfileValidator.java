package com.insurance.domain.validator.customerprofile.impl;

import com.insurance.domain.enums.OrderStatusEnum;
import com.insurance.domain.model.Order;
import com.insurance.domain.validator.customerprofile.CustomerProfileValidator;
import lombok.extern.slf4j.Slf4j;

import static com.insurance.domain.enums.OrderStatusEnum.REJECTED;
import static com.insurance.domain.enums.OrderStatusEnum.VALIDATED;

@Slf4j
public abstract class AbstractCustomerProfileValidator implements CustomerProfileValidator {

    @Override
    public OrderStatusEnum execute(Order order) {
        log.info("Validate order[{}] for Profile Classification {}. customer[{}]", order.getId(), this.getClassificationEnum().name(), order.getCustomerId());
        return isCategoryValid(order) ? VALIDATED : REJECTED;
    }

    protected abstract boolean isCategoryValid(Order order);

}
