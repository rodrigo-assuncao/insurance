package com.insurance.application.dto;

import com.insurance.domain.enums.SubscriptionStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionNotificationDto {

    private String orderId;
    private SubscriptionStatusEnum status;

}
