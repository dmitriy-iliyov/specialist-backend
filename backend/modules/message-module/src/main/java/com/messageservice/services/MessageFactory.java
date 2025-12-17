package com.messageservice.services;

import com.messageservice.models.MessageDto;
import com.specialist.contracts.notification.ExternalAppointmentCancelEvent;

public interface MessageFactory {
    MessageDto toMessageDto(ExternalAppointmentCancelEvent event);
}
