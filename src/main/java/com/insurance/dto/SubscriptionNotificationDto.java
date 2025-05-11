package com.insurance.dto;

import com.insurance.enums.StatusEnum;
import com.insurance.enums.SubscriptionEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionNotificationDto {

    private String orderId;
    private SubscriptionEnum status;

}
