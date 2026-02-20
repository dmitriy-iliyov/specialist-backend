package com.specialist.notification;

import com.specialist.contracts.notification.ExternalAppointmentCancelEvent;

import java.util.List;

public interface AppointmentCancelEventSender {
    void sendEvents(List<ExternalAppointmentCancelEvent> events);
}
