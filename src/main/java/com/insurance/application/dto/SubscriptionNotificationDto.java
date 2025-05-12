package com.insurance.application.dto;

import com.insurance.domain.model.enums.SubscriptionEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionNotificationDto {

    private String orderId;
    private SubscriptionEnum status;

}
