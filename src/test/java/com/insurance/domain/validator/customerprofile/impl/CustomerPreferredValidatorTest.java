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
class CustomerPreferredValidatorTest {

    @InjectMocks
    private CustomerPreferredValidator validator;

    @Test
    void shouldValidatePreferred_LIFE_AboveLimit() {
        var order = ValidatorTestHelper.createOrder(CategoryEnum.LIFE, "900000");
        assertEquals(StatusEnum.VALIDATED, validator.execute(order));
    }

    @Test
    void shouldRejectPreferred_AUTO_BelowLimit() {
        var order = ValidatorTestHelper.createOrder(CategoryEnum.AUTO, "100000");
        assertEquals(StatusEnum.REJECTED, validator.execute(order));
    }

    @Test
    void shouldValidatePreferred_Default_ExactlyUnderLimit() {
        var order = ValidatorTestHelper.createOrder(CategoryEnum.BUSINESS, "370000");
        assertEquals(StatusEnum.VALIDATED, validator.execute(order));
    }

}