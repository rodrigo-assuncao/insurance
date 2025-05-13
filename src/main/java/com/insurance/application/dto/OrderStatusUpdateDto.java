package com.insurance.application.dto;

import com.insurance.domain.enums.OrderStatusEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusUpdateDto {

    private String orderId;
    private OrderStatusEnum status;

}
