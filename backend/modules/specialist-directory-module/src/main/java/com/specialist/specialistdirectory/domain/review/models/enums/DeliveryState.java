package com.specialist.specialistdirectory.domain.review.models.enums;

import com.specialist.specialistdirectory.exceptions.UnknownDeliveryStateException;
import lombok.Getter;

import java.util.Arrays;

public enum DeliveryState {
    PREPARE(1),
    READY_TO_SEND(2),
    SENT(3);

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
