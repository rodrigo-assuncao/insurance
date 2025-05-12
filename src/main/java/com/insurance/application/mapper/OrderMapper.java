package com.insurance.application.mapper;

import com.insurance.application.dto.request.OrderRequest;
import com.insurance.application.dto.response.OrderResponse;
import com.insurance.domain.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = HistoryMapper.class)
public interface OrderMapper {

    Order toEntity(OrderRequest orderRequest);
    OrderResponse toResponse(Order order);

}
