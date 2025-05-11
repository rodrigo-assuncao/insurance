package com.insurance.dto;

import com.insurance.enums.StatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderStatusUpdateDto {

    private String solicitationId;
    private StatusEnum status;

}
