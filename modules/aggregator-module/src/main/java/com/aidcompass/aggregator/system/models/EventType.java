package com.aidcompass.aggregator.system.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum EventType {
    VOLUNTEER_APPROVED("volunteer_approved"),
    APPOINTMENT_SCHEDULED("appointment_scheduled"),
    REMIND_ABOUT_APPOINTMENT("remind_about_appointment"),
    APPOINTMENT_CANCELED("appointment_canceled");

    private final String json;

    EventType(String json) {
        this.json = json;
    }

    @JsonValue
    public String toJson() {
        return this.json;
    }

    @JsonCreator
    public static EventType fromJson(String json) {
        return Arrays.stream(EventType.values())
                .filter(type -> type.getJson().equals(json))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}