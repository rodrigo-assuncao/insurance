package com.insurance.dto;

import com.insurance.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionNotificationDto {

    private String orderId;
    private StatusEnum status;

}
