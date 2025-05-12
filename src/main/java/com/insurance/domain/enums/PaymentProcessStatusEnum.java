package com.insurance.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentProcessStatusEnum {

    CONFIRMED(StatusEnum.PAYMENT_CONFIRMED), REFUSED(StatusEnum.REJECTED);

    private final StatusEnum orderStatus;

}
