package com.aidcompass.schedule.interval.mapper;

import com.aidcompass.schedule.interval.models.NearestIntervalEntity;
import com.aidcompass.schedule.interval.models.dto.IntervalResponseDto;
import com.aidcompass.schedule.interval.models.dto.NearestIntervalDto;
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
