package com.insurance.application.dto;

import com.insurance.domain.enums.StatusEnum;
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
