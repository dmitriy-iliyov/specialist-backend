package com.specialist.schedule.interval.mapper;

import com.specialist.schedule.interval.models.IntervalEntity;
import com.specialist.schedule.interval.models.dto.IntervalResponseDto;
import com.specialist.schedule.interval.models.dto.SystemIntervalCreatedDto;
import com.specialist.schedule.interval.models.dto.SystemIntervalUpdateDto;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(
      componentModel = MappingConstants.ComponentModel.SPRING,
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface IntervalMapper {

    IntervalEntity toEntity(UUID specialistId, SystemIntervalCreatedDto dto);

    IntervalResponseDto toDto(IntervalEntity entity);

    List<IntervalResponseDto> toDtoList(List<IntervalEntity> entityList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "specialistId", ignore = true)
    void updateEntityFromDto(SystemIntervalUpdateDto dto, @MappingTarget IntervalEntity entity);
}
