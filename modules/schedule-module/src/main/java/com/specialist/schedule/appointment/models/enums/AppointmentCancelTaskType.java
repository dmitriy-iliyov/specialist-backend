package com.specialist.schedule.appointment.models.enums;

import com.specialist.schedule.exceptions.UnsupportedAppointmentCancelTaskTypeException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AppointmentCancelTaskType {
    CANCEL_BATCH_BY_DATA(1),
    CANCEL_BATCH(2);

    private final int code;

    AppointmentCancelTaskType(int code) {
        this.code = code;
    }

    public static AppointmentCancelTaskType fromCode(int code) {
        return Arrays.stream(AppointmentCancelTaskType.values())
                .filter(type -> type.code == code)
                .findFirst()
                .orElseThrow(UnsupportedAppointmentCancelTaskTypeException::new);
    }
}
