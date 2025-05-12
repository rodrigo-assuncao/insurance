package com.insurance.domain.model.enums;

import com.insurance.domain.model.History;

import java.time.LocalDateTime;

public enum StatusEnum {

    RECEIVED, VALIDATED, PENDING, REJECTED, APPROVED, CANCELED, PAYMENT_CONFIRMED, SUBSCRIPTION_ALLOWED;

    public History createHistory() {
        return History.builder()
                .status(this)
                .timestamp(LocalDateTime.now())
                .build();
    }

}
