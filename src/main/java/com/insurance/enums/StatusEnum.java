package com.insurance.enums;

import com.insurance.model.History;

import java.time.LocalDateTime;

public enum StatusEnum {

    RECEIVED, VALIDATED, PENDING, REJECTED, APPROVED, CANCELED;

    public History createHistory() {
        return History.builder()
                .status(this)
                .timestamp(LocalDateTime.now())
                .build();
    }

}
