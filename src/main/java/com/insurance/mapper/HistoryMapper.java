package com.insurance.mapper;

import com.insurance.dto.HistoryDto;
import com.insurance.model.History;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    HistoryDto toDto(History history);

}
