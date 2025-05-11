package com.insurance.validator.customerprofile;

import com.insurance.enums.ProfileClassificationEnum;
import com.insurance.enums.StatusEnum;
import com.insurance.model.Order;

public interface CustomerProfileValidator {

    ProfileClassificationEnum getClassificationEnum();

    StatusEnum execute(Order order);

}
