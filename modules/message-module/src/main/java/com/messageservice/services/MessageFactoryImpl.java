package com.messageservice.services;

import com.messageservice.models.MessageDto;
import com.specialist.contracts.notification.ExternalAppointmentCancelEvent;
import org.springframework.stereotype.Service;

@Service
public class MessageFactoryImpl implements MessageFactory {

    @Override
    public MessageDto toMessageDto(ExternalAppointmentCancelEvent event) {
        return new MessageDto(null, null, null);
    }
}
