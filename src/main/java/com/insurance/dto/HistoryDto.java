package com.insurance.dto;

import com.insurance.enums.StatusEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class HistoryDto {

    private StatusEnum status;
    private LocalDateTime timestamp;

}
