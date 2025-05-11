package com.insurance.model;

import com.insurance.enums.CategoryEnum;
import com.insurance.enums.PaymentMethodEnum;
import com.insurance.enums.SaleChannelEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "order")
public class Order {

    @Id
    private UUID id;
    private UUID customerId;
    private UUID productId;
    private CategoryEnum category;
    private SaleChannelEnum salesChannel;
    private PaymentMethodEnum paymentMethod;
    private LocalDateTime createdAt;
    private LocalDateTime finishedAt;
    private BigDecimal totalMonthlyPremiumAmount;
    private BigDecimal insuredAmount;
    private Map<String, BigDecimal> coverages;
    private List<String> assistances;
    private List<History> history;

}
