package com.insurance.domain.validator.customerprofile.impl;

import com.insurance.domain.model.enums.CategoryEnum;
import com.insurance.domain.model.enums.StatusEnum;
import com.insurance.domain.validator.customerprofile.ValidatorTestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CustomerNoInformationValidatorTest {

    @InjectMocks
    private CustomerNoInformationValidator validator;

    @Test
    void shouldValidateNoInfo_LIFE_ExactlyLimit() {
        var order = ValidatorTestHelper.createOrder(CategoryEnum.LIFE, "200000");
        assertEquals(StatusEnum.VALIDATED, validator.execute(order));
    }

    @Test
    void shouldRejectNoInfo_AUTO_AboveLimit() {
        var order = ValidatorTestHelper.createOrder(CategoryEnum.AUTO, "100000");
        assertEquals(StatusEnum.REJECTED, validator.execute(order));
    }

    @Test
    void shouldRejectNoInfo_Default_AboveLimit() {
        var order = ValidatorTestHelper.createOrder(CategoryEnum.BUSINESS, "60000");
        assertEquals(StatusEnum.REJECTED, validator.execute(order));
    }

}