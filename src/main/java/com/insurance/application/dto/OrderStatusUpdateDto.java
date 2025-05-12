package com.insurance.application.dto;

import com.insurance.domain.model.enums.StatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderStatusUpdateDto {

    private String orderId;
    private StatusEnum status;

}
