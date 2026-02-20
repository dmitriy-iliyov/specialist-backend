package com.specialist.specialistdirectory.domain.specialist.models.enums;

import com.specialist.specialistdirectory.exceptions.UnknownApproverTypeCodeException;
import lombok.Getter;

import java.util.Arrays;

public enum ApproverType {
    ADMIN(1),
    SERVICE(2);

    @Getter
    private final int code;

    ApproverType(int code) {
        this.code = code;
    }

    public static ApproverType fromCode(int code) {
        return Arrays.stream(ApproverType.values())
                .filter(type -> type.getCode() == code)
                .findFirst()
                .orElseThrow(UnknownApproverTypeCodeException::new);
    }
}
