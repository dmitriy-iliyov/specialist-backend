package com.specialist.specialistdirectory.domain.specialist.models.enums;

import com.specialist.specialistdirectory.exceptions.UnknownSpecialistStatusCodeException;
import lombok.Getter;

import java.util.Arrays;

public enum SpecialistStatus {
    UNAPPROVED(1),
    APPROVED(2),
    MANAGED(3),
    RECALL(4);

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
}
