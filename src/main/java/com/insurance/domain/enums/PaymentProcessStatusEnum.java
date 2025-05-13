package com.insurance.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentProcessStatusEnum {

    CONFIRMED(OrderStatusEnum.PAYMENT_CONFIRMED), REFUSED(OrderStatusEnum.REJECTED);

    private final OrderStatusEnum orderStatus;

}
