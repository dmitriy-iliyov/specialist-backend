package com.aidcompass.utils;


import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@UtilityClass
public class InstantToLocalDataTimeConverter {

    public LocalDateTime convert(Instant dataTime) {
        return LocalDateTime.ofInstant(dataTime, ZoneId.systemDefault());
    }
}
