package com.insurance.domain.model;

import com.insurance.domain.enums.OrderCategoryEnum;
import com.insurance.domain.enums.PaymentMethodEnum;
import com.insurance.domain.enums.SaleChannelEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

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
    private OrderCategoryEnum category;
    private SaleChannelEnum salesChannel;
    private PaymentMethodEnum paymentMethod;
    private LocalDateTime createdAt;
    private LocalDateTime finishedAt;
    private BigDecimal totalMonthlyPremiumAmount;
    private BigDecimal insuredAmount;
    private Map<String, BigDecimal> coverages;
    private List<String> assistances;
    private List<History> history;

    public Optional<History> lastOrderHistory() {
        return this.history.stream()
                .sorted(Comparator.comparing(History::getTimestamp).reversed())
                .reduce((a, b) -> a);
    }

}
