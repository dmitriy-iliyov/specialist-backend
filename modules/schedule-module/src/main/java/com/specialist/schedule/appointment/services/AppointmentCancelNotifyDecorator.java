package com.specialist.schedule.appointment.services;

import com.specialist.contracts.schedule.AppointmentCancelEvent;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class AppointmentCancelNotificationDecorator implements AppointmentCancelService {

    @Value("${}")
    private String TOPIC;
    private final AppointmentCancelService delegate;
    private final KafkaTemplate<String, AppointmentCancelEvent> kafkaTemplate;

    public AppointmentCancelNotificationDecorator(@Qualifier("unifiedAppointmentService") AppointmentCancelService delegate) {
        this.delegate = delegate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public AppointmentResponseDto cancelById(Long id) {
        AppointmentResponseDto responseDto = delegate.cancelById(id);
        kafkaTemplate.send();
        return responseDto;
    }

    @Override
    public void cancelAllByDate(UUID participantId, LocalDate date) {
        delegate.cancelAllByDate(participantId, date);
    }

    @Override
    public void cancelAll(UUID participantId) {
        delegate.cancelAll(participantId);
    }
}
