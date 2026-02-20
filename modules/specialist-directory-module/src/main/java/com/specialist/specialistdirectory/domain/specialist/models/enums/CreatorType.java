package com.specialist.specialistdirectory.domain.specialist.models.enums;

import com.specialist.specialistdirectory.exceptions.UnknownCreatorTypeCodeException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CreatorType {
    USER(1),
    ADMIN(2),
    SERVICE(3),
    SPECIALIST(4);

    private final int code;

    CreatorType(int code) {
        this.code = code;
    }

    public static CreatorType fromCode(int code) {
        return Arrays.stream(CreatorType.values())
                .filter(type -> type.getCode() == code)
                .findFirst()
                .orElseThrow(UnknownCreatorTypeCodeException::new);
    }
}
