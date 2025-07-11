package com.aidcompass.message.information;

import com.aidcompass.message.exceptions.models.SendMessageException;
import com.aidcompass.message.information.dto.AppointmentCanceledDto;
import com.aidcompass.message.information.dto.AppointmentReminderDto;
import com.aidcompass.message.information.dto.AppointmentScheduledDto;
import com.aidcompass.message.message_services.MessageFactory;
import com.aidcompass.message.message_services.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class EmailInformationService implements InformationService {

    private final MessageService messageService;


    public EmailInformationService(@Qualifier("emailMessageService") MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void reminderNotification(List<AppointmentReminderDto> dtoList) {
        for (AppointmentReminderDto dto: dtoList) {
            reminderNotification(dto);
        }
    }

    @Async
    public void reminderNotification(AppointmentReminderDto dto) {
        try {
            log.info("Generate remind message to user with id={}", dto.customer().id());
            messageService.sendMessage(MessageFactory.customerReminder(dto));
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
            throw new SendMessageException();
        }
    }

    @Override
    public void onScheduleNotification(AppointmentScheduledDto dto) {
        try {
            messageService.sendMessage(MessageFactory.customerAppointmentScheduled(dto));
            messageService.sendMessage(MessageFactory.volunteerAppointmentScheduled(dto));
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
            throw new SendMessageException();
        }
    }

    @Override
    public void onCancelNotification(AppointmentCanceledDto dto) {

    }
}
