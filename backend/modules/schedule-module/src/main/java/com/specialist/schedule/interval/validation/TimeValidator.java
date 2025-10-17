package com.specialist.schedule.interval.validation;


import com.specialist.schedule.interval.models.marker.IntervalMarker;

public interface TimeValidator {
    boolean isIntervalValid(IntervalMarker dto);
    boolean isIntervalTimeValid(IntervalMarker dto);
}
