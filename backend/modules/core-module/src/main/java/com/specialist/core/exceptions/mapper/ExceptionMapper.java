package com.specialist.core.exceptions.mapper;

import com.specialist.core.exceptions.models.dto.ExceptionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ExceptionMapper {
    ExceptionResponseDto toDto(Exception exception);
}
