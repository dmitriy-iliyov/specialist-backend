package com.aidcompass.schedule.interval.mapper;

import com.aidcompass.schedule.interval.models.IntervalEntity;
import com.aidcompass.schedule.interval.models.dto.IntervalResponseDto;
import com.aidcompass.schedule.interval.models.dto.SystemIntervalCreatedDto;
import com.aidcompass.schedule.interval.models.dto.SystemIntervalUpdateDto;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(
      componentModel = MappingConstants.ComponentModel.SPRING,
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface IntervalMapper {

    IntervalEntity toEntity(UUID ownerId, SystemIntervalCreatedDto dto);

    IntervalResponseDto toDto(IntervalEntity entity);

    List<IntervalResponseDto> toDtoList(List<IntervalEntity> entityList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    void updateEntityFromDto(SystemIntervalUpdateDto dto, @MappingTarget IntervalEntity entity);
}
