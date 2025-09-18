package com.specialist.schedule.interval.mapper;

import com.specialist.schedule.interval.models.NearestIntervalEntity;
import com.specialist.schedule.interval.models.dto.IntervalResponseDto;
import com.specialist.schedule.interval.models.dto.NearestIntervalDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface NearestIntervalMapper {

    NearestIntervalEntity toEntity(IntervalResponseDto dto);

    NearestIntervalDto toDto(NearestIntervalEntity entity);

    List<NearestIntervalDto> toDtoList(Iterable<NearestIntervalEntity> entityList);
}