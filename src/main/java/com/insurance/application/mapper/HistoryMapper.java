package com.insurance.application.mapper;

import com.insurance.application.dto.HistoryDto;
import com.insurance.domain.model.History;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    HistoryDto toDto(History history);

}
