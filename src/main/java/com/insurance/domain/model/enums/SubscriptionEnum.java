package com.insurance.domain.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.insurance.domain.model.enums.StatusEnum.REJECTED;
import static com.insurance.domain.model.enums.StatusEnum.SUBSCRIPTION_ALLOWED;

@Getter
@RequiredArgsConstructor
public enum SubscriptionEnum {

    ALLOWED(SUBSCRIPTION_ALLOWED), DENIED(REJECTED);

    private final StatusEnum orderStatus;

}
