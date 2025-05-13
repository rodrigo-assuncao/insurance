package com.insurance.domain.model;

import com.insurance.domain.enums.OrderStatusEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {

    private OrderStatusEnum status;
    private LocalDateTime timestamp;

}
