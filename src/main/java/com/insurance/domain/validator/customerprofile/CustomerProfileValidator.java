package com.insurance.domain.validator.customerprofile;

import com.insurance.domain.enums.ProfileClassificationEnum;
import com.insurance.domain.enums.OrderStatusEnum;
import com.insurance.domain.model.Order;

public interface CustomerProfileValidator {

    ProfileClassificationEnum getClassificationEnum();

    OrderStatusEnum execute(Order order);

}
