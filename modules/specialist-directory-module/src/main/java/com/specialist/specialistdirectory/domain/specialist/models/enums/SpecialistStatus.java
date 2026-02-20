package com.specialist.specialistdirectory.domain.specialist.models.enums;

import com.specialist.specialistdirectory.exceptions.UnknownSpecialistStatusCodeException;
import com.specialist.specialistdirectory.exceptions.UnsupportedSpecialistStatusCodeException;
import lombok.Getter;

import java.util.Arrays;

public enum SpecialistStatus {
    UNAPPROVED(1),
    APPROVED(2),
    SUSPENDED(3);

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

    public static SpecialistStatus fromJson(String json) {
        return Arrays.stream(SpecialistStatus.values())
                .filter(status -> status.name().equals(json))
                .findFirst()
                .orElseThrow(UnsupportedSpecialistStatusCodeException::new);
    }
}
