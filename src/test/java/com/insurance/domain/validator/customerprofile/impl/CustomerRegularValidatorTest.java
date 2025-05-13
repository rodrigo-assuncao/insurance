package com.insurance.domain.validator.customerprofile.impl;

import com.insurance.domain.enums.OrderCategoryEnum;
import com.insurance.domain.enums.OrderStatusEnum;
import com.insurance.domain.validator.customerprofile.ValidatorTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CustomerRegularValidatorTest {

    @InjectMocks
    private CustomerRegularValidator validator;

    @Test
    void shouldValidateRegular_AUTO_BelowLimit() {
        var order = ValidatorTestHelper.createOrder(OrderCategoryEnum.AUTO, "200000");
        assertEquals(OrderStatusEnum.VALIDATED, validator.execute(order));
    }

    @Test
    void shouldRejectRegular_RESIDENTIAL_AboveLimit() {
        var order = ValidatorTestHelper.createOrder(OrderCategoryEnum.RESIDENTIAL, "160000");
        assertEquals(OrderStatusEnum.REJECTED, validator.execute(order));
    }

    @Test
    void shouldRejectRegular_Default_AboveLimit() {
        var order = ValidatorTestHelper.createOrder(OrderCategoryEnum.BUSINESS, "130000");
        assertEquals(OrderStatusEnum.REJECTED, validator.execute(order));
    }

}