package com.specialist.schedule.appointment.repositories;

import com.specialist.schedule.appointment.models.enums.AppointmentCancelTaskType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class AppointmentCancelTaskTypeConverter implements AttributeConverter<AppointmentCancelTaskType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AppointmentCancelTaskType appointmentCancelTaskType) {
        return appointmentCancelTaskType.getCode();
    }

    @Override
    public AppointmentCancelTaskType convertToEntityAttribute(Integer integer) {
        return AppointmentCancelTaskType.fromCode(integer);
    }
}
