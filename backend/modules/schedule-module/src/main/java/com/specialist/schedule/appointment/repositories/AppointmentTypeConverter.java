package com.specialist.schedule.appointment.repositories;

import com.specialist.schedule.appointment.models.enums.AppointmentType;
import com.specialist.schedule.exceptions.appointment.AppointmentTypeConvertException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class AppointmentTypeConverter implements AttributeConverter<AppointmentType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AppointmentType type) {
        if (type == null) {
            throw new AppointmentTypeConvertException();
        }
        return type.getCode();
    }

    @Override
    public AppointmentType convertToEntityAttribute(Integer code) {
        if (code == null) {
            throw new AppointmentTypeConvertException();
        }
        return AppointmentType.fromCode(code);
    }
}
