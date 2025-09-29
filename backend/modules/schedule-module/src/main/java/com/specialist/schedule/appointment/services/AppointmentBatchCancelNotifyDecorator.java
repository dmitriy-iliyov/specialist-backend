package com.specialist.schedule.appointment.services;

import com.specialist.contracts.notification.InternalAppointmentCancelEvent;
import com.specialist.schedule.appointment.mapper.AppointmentMapper;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.utils.pagination.BatchResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class AppointmentBatchCancelNotifyDecorator implements AppointmentBatchCancelService {

    private final AppointmentBatchCancelService delegate;
    private final AppointmentMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    public AppointmentBatchCancelNotifyDecorator(@Qualifier("appointmentBatchCancelDeferDecorator") AppointmentBatchCancelService delegate,
                                                 AppointmentMapper mapper, ApplicationEventPublisher eventPublisher) {
        this.delegate = delegate;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public BatchResponse<AppointmentResponseDto> cancelBatchByDate(UUID participantId, LocalDate date) {
        BatchResponse<AppointmentResponseDto> batch = delegate.cancelBatchByDate(participantId, date);
        eventPublisher.publishEvent(new InternalAppointmentCancelEvent(participantId, mapper.toSystemDtoList(batch.data())));
        return batch;
    }

    @Override
    public BatchResponse<AppointmentResponseDto> cancelBatch(UUID participantId) {
        BatchResponse<AppointmentResponseDto> batch = delegate.cancelBatch(participantId);
        eventPublisher.publishEvent(new InternalAppointmentCancelEvent(participantId, mapper.toSystemDtoList(batch.data())));
        return batch;
    }
}
