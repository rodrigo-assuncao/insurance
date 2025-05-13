package com.insurance.application.dto;

import com.insurance.domain.enums.PaymentProcessStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentNotificationDto {

    private String orderId;
    private PaymentProcessStatusEnum status;

}
