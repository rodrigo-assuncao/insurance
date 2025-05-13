package com.insurance.application.dto;

import com.insurance.domain.enums.OrderStatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class HistoryDto {

    private OrderStatusEnum status;
    private LocalDateTime timestamp;

}
