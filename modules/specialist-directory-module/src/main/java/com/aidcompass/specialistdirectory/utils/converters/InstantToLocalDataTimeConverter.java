package com.aidcompass.specialistdirectory.utils.converters;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class InstantToLocalDataTimeConverter {

    public LocalDateTime convert(Instant dataTime) {
        return LocalDateTime.ofInstant(dataTime, ZoneId.systemDefault());
    }
}
