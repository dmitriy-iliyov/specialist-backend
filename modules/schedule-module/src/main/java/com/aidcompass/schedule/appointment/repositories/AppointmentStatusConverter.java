package com.aidcompass.schedule.appointment.repositories;

import com.aidcompass.schedule.appointment.models.enums.AppointmentStatus;
import com.aidcompass.schedule.exceptions.appointment.AppointmentStatusConvertException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class AppointmentStatusConverter implements AttributeConverter<AppointmentStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AppointmentStatus status) {
        if (status == null) {
            throw new AppointmentStatusConvertException();
        }
        return status.getCode();
    }

    @Override
    public AppointmentStatus convertToEntityAttribute(Integer code) {
        if (code == null) {
            throw new AppointmentStatusConvertException();
        }
        return AppointmentStatus.fromCode(code);
    }
}
