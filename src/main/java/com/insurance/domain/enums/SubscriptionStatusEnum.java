package com.insurance.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.insurance.domain.enums.StatusEnum.REJECTED;
import static com.insurance.domain.enums.StatusEnum.SUBSCRIPTION_ALLOWED;

@Getter
@RequiredArgsConstructor
public enum SubscriptionStatusEnum {

    ALLOWED(SUBSCRIPTION_ALLOWED), DENIED(REJECTED);

    private final StatusEnum orderStatus;

}
