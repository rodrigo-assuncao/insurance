package com.insurance.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.insurance.enums.CategoryEnum;
import com.insurance.enums.PaymentMethodEnum;
import com.insurance.enums.SaleChannelEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class OrderRequest {

    @JsonProperty("customer_id")
    private UUID customerId;

    @JsonProperty("product_id")
    private UUID productId;
    private CategoryEnum category;
    private SaleChannelEnum salesChannel;
    private PaymentMethodEnum paymentMethod;
    private Map<String, BigDecimal> coverages;
    private List<String> assistances;

    @JsonProperty("total_monthly_premium_amount")
    private BigDecimal totalMonthlyPremiumAmount;

    @JsonProperty("insured_amount")
    private BigDecimal insuredAmount;

}
