package com.insurance.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insurance.application.dto.request.OrderRequest;
import com.insurance.application.dto.response.OrderResponse;
import com.insurance.domain.usecase.ProcessOrder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProcessOrder processOrder;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateOrder() throws Exception {
        var request = new OrderRequest();
        request.setCustomerId(UUID.randomUUID());

        var response = new OrderResponse();
        response.setId(UUID.randomUUID());

        Mockito.when(processOrder.createOrder(any())).thenReturn(response);

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.getId().toString()));
    }

    @Test
    void shouldFindOrderById() throws Exception {
        var orderId = UUID.randomUUID();

        OrderResponse response = new OrderResponse();
        response.setId(orderId);

        Mockito.when(processOrder.findOrderByOrderId(orderId.toString())).thenReturn(response);

        mockMvc.perform(get("/order/{orderId}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId.toString()));
    }

    @Test
    void shouldCancelOrder() throws Exception {
        var orderId = UUID.randomUUID();

        OrderResponse response = new OrderResponse();
        response.setId(orderId);

        Mockito.when(processOrder.cancelOrder(orderId.toString())).thenReturn(response);

        mockMvc.perform(post("/order/{orderId}/cancel", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId.toString()));
    }
}