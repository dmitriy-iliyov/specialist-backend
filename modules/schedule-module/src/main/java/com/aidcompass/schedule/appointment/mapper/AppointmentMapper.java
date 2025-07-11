package com.aidcompass.schedule.appointment.mapper;


import com.aidcompass.schedule.appointment.models.AppointmentEntity;
import com.aidcompass.schedule.appointment.models.dto.AppointmentCreateDto;
import com.aidcompass.schedule.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.schedule.appointment.models.dto.AppointmentUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AppointmentMapper {

    @Mapping(target = "type", expression = "java(com.aidcompass.schedule.appointment.models.enums.AppointmentType.valueOf(dto.getType()))")
    AppointmentEntity toEntity(UUID customerId, AppointmentCreateDto dto);

    AppointmentResponseDto toDto(AppointmentEntity dto);

    List<AppointmentResponseDto> toDtoList(List<AppointmentEntity> entityList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "volunteerId", ignore = true)
    @Mapping(target = "type", expression = "java(com.aidcompass.schedule.appointment.models.enums.AppointmentType.valueOf(dto.getType()))")
    void updateEntityFromUpdateDto(AppointmentUpdateDto dto, @MappingTarget AppointmentEntity entity);
}