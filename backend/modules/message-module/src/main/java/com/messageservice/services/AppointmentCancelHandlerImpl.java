package com.messageservice.services;

import com.messageservice.models.MessageDto;
import com.specialist.contracts.notification.ExternalAppointmentCancelEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AppointmentCancelHandlerImpl implements AppointmentCancelHandler {

    private final MessageService messageService;
    private final MessageFactory messageFactory;

    public AppointmentCancelHandlerImpl(@Qualifier("notificationEmailService") MessageService messageService,
                                        MessageFactory messageFactory) {
        this.messageService = messageService;
        this.messageFactory = messageFactory;
    }

    @Override
    public void handle(ExternalAppointmentCancelEvent event) throws Exception {
        MessageDto dto = messageFactory.toMessageDto(event);
        messageService.sendMessage(dto);
    }
}
