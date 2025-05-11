package com.insurance.mapper;

import com.insurance.dto.request.OrderRequest;
import com.insurance.dto.response.OrderResponse;
import com.insurance.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = HistoryMapper.class)
public interface OrderMapper {

    Order toEntity(OrderRequest orderRequest);
    OrderResponse toResponse(Order order);

}
