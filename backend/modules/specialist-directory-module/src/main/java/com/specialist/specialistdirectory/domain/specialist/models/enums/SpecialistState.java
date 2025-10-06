package com.specialist.specialistdirectory.domain.specialist.models.enums;

import com.specialist.specialistdirectory.exceptions.UnknownSpecialistStatusCodeException;
import com.specialist.specialistdirectory.exceptions.UnsupportedSpecialistStatusCodeException;
import lombok.Getter;

import java.util.Arrays;

public enum SpecialistState {
    DEFAULT(1),
    MANAGED(2),
    RECALLED(3);

    @Getter
    private final int code;

    SpecialistState(int code) {
        this.code = code;
    }

    public static SpecialistState fromCode(int code) {
        return Arrays.stream(SpecialistState.values())
                .filter(status -> status.getCode() == code)
                .findFirst()
                .orElseThrow(UnknownSpecialistStatusCodeException::new);
    }

    public static SpecialistState fromJson(String json) {
        return Arrays.stream(SpecialistState.values())
                .filter(status -> status.name().equals(json))
                .findFirst()
                .orElseThrow(UnsupportedSpecialistStatusCodeException::new);
    }
}
