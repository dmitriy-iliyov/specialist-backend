package com.aidcompass.schedule.appointment.models.marker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public interface AppointmentMarker {
    LocalTime getStart();
    LocalTime getEnd();
    LocalDate getDate();
    UUID getVolunteerId();
}

