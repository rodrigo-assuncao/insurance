package com.insurance.dto;

import com.insurance.enums.PaymentProcessEnum;
import com.insurance.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentNotificationDto {

    private String orderId;
    private PaymentProcessEnum status;

}
