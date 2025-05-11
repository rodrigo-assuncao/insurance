package com.insurance.model;

import com.insurance.dto.HistoryDto;
import com.insurance.enums.StatusEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {

    private StatusEnum status;
    private LocalDateTime timestamp;

}
