package com.aidcompass.schedule.interval.validation.time;

import com.aidcompass.schedule.interval.models.marker.IntervalMarker;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class TimeValidatorImpl implements TimeValidator {

    private final LocalTime WORK_DAY_START = LocalTime.of(8, 0);
    private final LocalTime WORK_DAY_END = LocalTime.of(20, 0);


    @Override
    public boolean isIntervalValid(IntervalMarker dto) {
        return dto.start().isBefore(dto.end());
    }

    @Override
    public boolean isIntervalTimeValid(IntervalMarker dto) {
        if (WORK_DAY_START.isAfter(dto.start())) {
            return false;
        }
        if (WORK_DAY_END.isBefore(dto.end())) {
            return false;
        }
        return true;
    }
}
