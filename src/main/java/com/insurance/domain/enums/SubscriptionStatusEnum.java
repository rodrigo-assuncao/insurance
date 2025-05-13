package com.insurance.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.insurance.domain.enums.OrderStatusEnum.REJECTED;
import static com.insurance.domain.enums.OrderStatusEnum.SUBSCRIPTION_ALLOWED;

@Getter
@RequiredArgsConstructor
public enum SubscriptionStatusEnum {

    ALLOWED(SUBSCRIPTION_ALLOWED), DENIED(REJECTED);

    private final OrderStatusEnum orderStatus;

}
