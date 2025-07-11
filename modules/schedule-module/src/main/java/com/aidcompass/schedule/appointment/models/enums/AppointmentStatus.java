package com.aidcompass.schedule.appointment.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

public enum AppointmentStatus {
    SCHEDULED("Заплановано", 0),
    COMPLETED("Завершено", 1),
    CANCELED("Скасовано", 2),
    SKIPPED("Пропущено", 3);
    
    private final String translate;
    @Getter
    private final int code;

    AppointmentStatus(String translate, int code) {
        this.translate = translate;
        this.code = code;
    }

    @JsonValue
    public String getTranslate() {
        return translate;
    }

    @Override
    public String toString() {
        return translate;
    }

    public static AppointmentStatus fromCode(int code) {
        return Arrays.stream(AppointmentStatus.values())
                .filter(status -> status.getCode() == code)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
