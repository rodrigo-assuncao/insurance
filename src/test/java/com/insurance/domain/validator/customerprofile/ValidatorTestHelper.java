package com.insurance.domain.validator.customerprofile;

import com.insurance.domain.enums.OrderCategoryEnum;
import com.insurance.domain.model.Order;

import java.math.BigDecimal;
import java.util.UUID;

public class ValidatorTestHelper {

    public static Order createOrder(OrderCategoryEnum category, String insuredAmount) {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setCustomerId(UUID.randomUUID());
        order.setCategory(category);
        order.setInsuredAmount(new BigDecimal(insuredAmount));
        return order;
    }

}
