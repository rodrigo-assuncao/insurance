package com.insurance.domain.validator.customerprofile;

import com.insurance.domain.enums.ProfileClassificationEnum;
import com.insurance.domain.validator.customerprofile.impl.CustomerHighRiskValidator;
import com.insurance.domain.validator.customerprofile.impl.CustomerNoInformationValidator;
import com.insurance.domain.validator.customerprofile.impl.CustomerPreferredValidator;
import com.insurance.domain.validator.customerprofile.impl.CustomerRegularValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerProfileValidatorFactoryTest {

    @Autowired
    private CustomerProfileValidatorFactory factory;

    @Test
    void shouldReturnHighRiskValidator() {
        validateInstance(ProfileClassificationEnum.HIGH_RISK, CustomerHighRiskValidator.class);
    }

    @Test
    void shouldReturnPreferredValidator() {
        validateInstance(ProfileClassificationEnum.PREFERRED, CustomerPreferredValidator.class);
    }

    @Test
    void shouldReturnRegularValidator() {
        validateInstance(ProfileClassificationEnum.REGULAR, CustomerRegularValidator.class);
    }

    @Test
    void shouldReturnNoInformationValidator() {
        validateInstance(ProfileClassificationEnum.NO_INFORMATION, CustomerNoInformationValidator.class);
    }

    private void validateInstance(ProfileClassificationEnum classification, Class instance) {
        CustomerProfileValidator validator = factory.getCustomerProfileValidatorByClassification(classification);
        assertThat(validator).isInstanceOf(instance);
    }

}