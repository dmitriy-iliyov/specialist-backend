package com.aidcompass.schedule.appointment.models.enums;

import lombok.Getter;

import java.util.Arrays;

public enum AppointmentType {
    ONLINE(0),
    OFFLINE(1);

    @Getter
    private final int code;

    AppointmentType(int code) {
        this.code = code;
    }

    public static AppointmentType fromCode(int code) {
        return Arrays.stream(AppointmentType.values()).filter(type -> type.getCode() == code)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
