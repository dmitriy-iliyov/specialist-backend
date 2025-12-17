package com.messageservice.services;

import com.specialist.contracts.notification.ExternalAppointmentCancelEvent;

public interface AppointmentCancelHandler {
    void handle(ExternalAppointmentCancelEvent event) throws Exception;
}
