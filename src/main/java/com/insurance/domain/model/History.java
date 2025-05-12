package com.insurance.domain.model;

import com.insurance.domain.enums.StatusEnum;
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
