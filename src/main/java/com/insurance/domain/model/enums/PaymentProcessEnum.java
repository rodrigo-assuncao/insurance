package com.insurance.domain.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentProcessEnum {

    CONFIRMED(StatusEnum.PAYMENT_CONFIRMED), REFUSED(StatusEnum.REJECTED);

    private final StatusEnum orderStatus;

}
