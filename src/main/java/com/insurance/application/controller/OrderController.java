package com.insurance.application.controller;


import com.insurance.application.dto.request.OrderRequest;
import com.insurance.application.dto.response.OrderResponse;
import com.insurance.domain.usecase.ProcessOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final ProcessOrder processOrder;

    @PostMapping
    @ResponseStatus(CREATED)
    public OrderResponse createOrder(
            @RequestBody OrderRequest request
    ) {
        return this.processOrder.createOrder(request);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(OK)
    public OrderResponse findOrderByOrderId(
            @PathVariable("orderId") String orderId
    ) {
        return this.processOrder.findOrderByOrderId(orderId);
    }

    @PostMapping("/{orderId}/cancel")
    @ResponseStatus(OK)
    public OrderResponse cancelOrder(
            @PathVariable("orderId") String orderId
    ) {
        return this.processOrder.cancelOrder(orderId);
    }

}
