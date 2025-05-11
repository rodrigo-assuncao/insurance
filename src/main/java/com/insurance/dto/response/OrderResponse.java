package com.insurance.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.insurance.dto.HistoryDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class OrderResponse {

    private UUID id;

    @JsonProperty("customer_id")
    private UUID customerId;

    @JsonProperty("product_id")
    private UUID productId;
    private String category;
    private String salesChannel;
    private String paymentMethod;
    private LocalDateTime createdAt;
    private LocalDateTime finishedAt;
    private Map<String, BigDecimal> coverages;
    private List<String> assistances;
    private List<HistoryDto> history;

    @JsonProperty("total_monthly_premium_amount")
    private BigDecimal totalMonthlyPremiumAmount;

    @JsonProperty("insured_amount")
    private BigDecimal insuredAmount;

}
