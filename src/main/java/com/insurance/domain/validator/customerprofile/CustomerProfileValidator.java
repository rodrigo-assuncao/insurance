package com.insurance.domain.validator.customerprofile;

import com.insurance.domain.model.Order;
import com.insurance.domain.model.enums.ProfileClassificationEnum;
import com.insurance.domain.model.enums.StatusEnum;

public interface CustomerProfileValidator {

    ProfileClassificationEnum getClassificationEnum();

    StatusEnum execute(Order order);

}
