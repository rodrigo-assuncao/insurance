package com.insurance.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.insurance.enums.StatusEnum.*;

@Getter
@RequiredArgsConstructor
public enum SubscriptionEnum {

    ALLOWED(SUBSCRIPTION_ALLOWED), DENIED(REJECTED);

    private final StatusEnum orderStatus;

}
