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
class CustomerHighRiskValidatorTest {

    @InjectMocks
    private CustomerHighRiskValidator validator;

    @Test
    void shouldValidateHighRisk_LIFE_BelowLimit() {
        var order = ValidatorTestHelper.createOrder(CategoryEnum.LIFE, "499999");
        assertEquals(StatusEnum.VALIDATED, validator.execute(order));
    }

    @Test
    void shouldRejectHighRisk_AUTO_AboveLimit() {
        var order = ValidatorTestHelper.createOrder(CategoryEnum.AUTO, "400000");
        assertEquals(StatusEnum.REJECTED, validator.execute(order));
    }

    @Test
    void shouldValidateHighRisk_Default_BelowLimit() {
        var order = ValidatorTestHelper.createOrder(CategoryEnum.BUSINESS, "250000");
        assertEquals(StatusEnum.VALIDATED, validator.execute(order));
    }

}