package com.insurance.application.dto;

import com.insurance.domain.enums.PaymentProcessStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentNotificationDto {

    private String orderId;
    private PaymentProcessStatusEnum status;

}
