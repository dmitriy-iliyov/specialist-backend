package com.aidcompass.schedule.interval.validation.time;


import com.aidcompass.schedule.interval.models.marker.IntervalMarker;

public interface TimeValidator {

    boolean isIntervalValid(IntervalMarker dto);

    boolean isIntervalTimeValid(IntervalMarker dto);
}
