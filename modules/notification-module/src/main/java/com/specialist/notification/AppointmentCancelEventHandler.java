package com.specialist.notification;


import com.specialist.contracts.notification.InternalAppointmentCancelEvent;

public interface AppointmentCancelEventHandler {
    void handle(InternalAppointmentCancelEvent event);
}
