package com.insurance.application.dto;

import com.insurance.domain.model.enums.PaymentProcessEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentNotificationDto {

    private String orderId;
    private PaymentProcessEnum status;

}
