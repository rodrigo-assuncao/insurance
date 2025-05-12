package com.insurance.application.dto;

import com.insurance.domain.enums.SubscriptionStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionNotificationDto {

    private String orderId;
    private SubscriptionStatusEnum status;

}
