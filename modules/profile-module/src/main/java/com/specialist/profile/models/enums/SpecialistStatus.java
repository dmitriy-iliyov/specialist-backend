package com.specialist.profile.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.specialist.profile.exceptions.UnknownSpecialistStatusCodeException;
import lombok.Getter;

import java.util.Arrays;

public enum SpecialistStatus {
    UNAPPROVED(1),
    APPROVED(2);

    @Getter
    private final int code;

    SpecialistStatus(int code) {
        this.code = code;
    }

    public static SpecialistStatus fromCode(int code) {
        return Arrays.stream(SpecialistStatus.values())
                .filter(status -> status.getCode() == code)
                .findFirst()
                .orElseThrow(UnknownSpecialistStatusCodeException::new);
    }

    @JsonCreator
    public static SpecialistStatus fromJson(String json) {
        return Arrays.stream(SpecialistStatus.values())
                .filter(status -> status.name().equalsIgnoreCase(json))
                .findFirst()
                .orElse(null);
    }
}
