package com.specialist.schedule.appointment.mapper;

import com.specialist.schedule.appointment.models.AppointmentCancelTaskEntity;
import com.specialist.schedule.appointment.models.dto.AppointmentCancelTaskCreateDto;
import com.specialist.schedule.appointment.models.dto.AppointmentCancelTaskResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppointmentCancelTaskMapper {

    AppointmentCancelTaskEntity toEntity(AppointmentCancelTaskCreateDto dto);

    List<AppointmentCancelTaskEntity> toEntityList(Set<AppointmentCancelTaskCreateDto> dtoSet);

    AppointmentCancelTaskResponseDto toDto(AppointmentCancelTaskEntity entity);

    List<AppointmentCancelTaskResponseDto> toDtoList(List<AppointmentCancelTaskEntity> entityList);
}
