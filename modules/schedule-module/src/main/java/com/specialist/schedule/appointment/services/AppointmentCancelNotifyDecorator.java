package com.specialist.schedule.appointment.services;

import com.specialist.contracts.notification.InternalAppointmentCancelEvent;
import com.specialist.schedule.appointment.mapper.AppointmentMapper;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.utils.pagination.BatchResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentCancelNotifyDecorator implements AppointmentCancelService {

    private final AppointmentCancelService delegate;
    private final AppointmentMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    public AppointmentCancelNotifyDecorator(@Qualifier("appointmentCancelDeferDecorator") AppointmentCancelService delegate,
                                            AppointmentMapper mapper, ApplicationEventPublisher eventPublisher) {
        this.delegate = delegate;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public AppointmentResponseDto cancelById(Long id) {
        AppointmentResponseDto dto = delegate.cancelById(id);
        eventPublisher.publishEvent(new InternalAppointmentCancelEvent(List.of(mapper.toSystemDto(dto))));
        return dto;
    }

    @Override
    public BatchResponse<AppointmentResponseDto> cancelBatchByDate(UUID participantId, LocalDate date) {
        BatchResponse<AppointmentResponseDto> batch = delegate.cancelBatchByDate(participantId, date);
        eventPublisher.publishEvent(new InternalAppointmentCancelEvent(mapper.toSystemDtoList(batch.data())));
        return batch;
    }

    @Override
    public BatchResponse<AppointmentResponseDto> cancelBatch(UUID participantId) {
        BatchResponse<AppointmentResponseDto> batch = delegate.cancelBatch(participantId);
        eventPublisher.publishEvent(new InternalAppointmentCancelEvent(mapper.toSystemDtoList(batch.data())));
        return batch;
    }
}
