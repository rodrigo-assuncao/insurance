package com.insurance.domain.validator.customerprofile;

import com.insurance.domain.model.Order;
import com.insurance.domain.enums.CategoryEnum;

import java.math.BigDecimal;
import java.util.UUID;

public class ValidatorTestHelper {

    public static Order createOrder(CategoryEnum category, String insuredAmount) {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setCustomerId(UUID.randomUUID());
        order.setCategory(category);
        order.setInsuredAmount(new BigDecimal(insuredAmount));
        return order;
    }

}
