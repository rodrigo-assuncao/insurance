package com.insurance.validator.customerprofile;

import com.insurance.enums.ProfileClassificationEnum;
import com.insurance.validator.customerprofile.impl.AbstractCustomerProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CustomerProfileValidatorFactory {

    private final Map<ProfileClassificationEnum, AbstractCustomerProfileValidator> customerProfileValidatorMap;

    @Autowired
    public CustomerProfileValidatorFactory(
            List<AbstractCustomerProfileValidator> customerProfileValidatorList
    ) {
        customerProfileValidatorMap = customerProfileValidatorList.stream()
                .collect(Collectors.toMap(CustomerProfileValidator::getClassificationEnum, Function.identity()));
    }

    public CustomerProfileValidator getCustomerProfileValidatorByClassification(ProfileClassificationEnum classification) {
        return this.customerProfileValidatorMap.get(classification);
    }

}
