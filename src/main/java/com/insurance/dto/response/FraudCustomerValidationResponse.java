package com.insurance.dto.response;

import com.insurance.enums.ProfileClassificationEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class FraudCustomerValidationResponse {

    private String orderId;
    private String customerId;
    private LocalDateTime analyzedAt;
    private ProfileClassificationEnum classification;
    private List<OccurrenceDto> occurrences;

}
