package com.aidcompass.specialistdirectory.domain.review.models.enums;

import com.aidcompass.specialistdirectory.exceptions.UnknownDeliveryStateException;
import lombok.Getter;

import java.util.Arrays;

public enum DeliveryState {
    PREPARE(1),
    READY(2);

    @Getter
    private final int code;

    DeliveryState(int code) {
        this.code = code;
    }

    public static DeliveryState fromCode(int code) {
        return Arrays.stream(DeliveryState.values())
                .filter(state -> state.getCode() == code)
                .findFirst()
                .orElseThrow(UnknownDeliveryStateException::new);
    }
}
