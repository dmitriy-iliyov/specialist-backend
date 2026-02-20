package com.specialist.schedule.appointment.services;

import com.specialist.contracts.notification.InternalAppointmentCancelEvent;
import com.specialist.schedule.appointment.mapper.AppointmentMapper;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.utils.pagination.BatchResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;
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

    @Transactional
    @Override
    public BatchResponse<AppointmentResponseDto> cancelBatchByDate(UUID participantId, LocalDate date) {
        BatchResponse<AppointmentResponseDto> batch = delegate.cancelBatchByDate(participantId, date);
        // FIXME: need specific algorithm for preparing before sending
        eventPublisher.publishEvent(
                new InternalAppointmentCancelEvent(Set.of(participantId), mapper.toSystemDtoList(batch.data()))
        );
        return batch;
    }

    @Override
    public BatchResponse<AppointmentResponseDto> cancelBatchByDate(Set<UUID> ids, LocalDate date) {
        BatchResponse<AppointmentResponseDto> batch = delegate.cancelBatchByDate(ids, date);
        // FIXME: need specific algorithm for preparing before sending
        eventPublisher.publishEvent(new InternalAppointmentCancelEvent(ids, mapper.toSystemDtoList(batch.data())));
        return batch;
    }

    @Override
    public BatchResponse<AppointmentResponseDto> cancelBatch(Set<UUID> participantIds) {
        BatchResponse<AppointmentResponseDto> batch = delegate.cancelBatch(participantIds);
        eventPublisher.publishEvent(
                new InternalAppointmentCancelEvent(participantIds, mapper.toSystemDtoList(batch.data()))
        );
        return batch;
    }
}
