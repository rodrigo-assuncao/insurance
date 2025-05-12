package com.insurance.domain.validator.customerprofile.impl;

import com.insurance.domain.enums.CategoryEnum;
import com.insurance.domain.enums.StatusEnum;
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
        var order = ValidatorTestHelper.createOrder(CategoryEnum.AUTO, "200000");
        assertEquals(StatusEnum.VALIDATED, validator.execute(order));
    }

    @Test
    void shouldRejectRegular_RESIDENTIAL_AboveLimit() {
        var order = ValidatorTestHelper.createOrder(CategoryEnum.RESIDENTIAL, "160000");
        assertEquals(StatusEnum.REJECTED, validator.execute(order));
    }

    @Test
    void shouldRejectRegular_Default_AboveLimit() {
        var order = ValidatorTestHelper.createOrder(CategoryEnum.BUSINESS, "130000");
        assertEquals(StatusEnum.REJECTED, validator.execute(order));
    }

}