package com.specialist.schedule.appointment.services;

import com.specialist.schedule.appointment.mapper.AppointmentMapper;
import com.specialist.schedule.appointment.models.dto.AppointmentResponseDto;
import com.specialist.utils.pagination.BatchResponse;
import io.github.dmitriyiliyov.springoutbox.publisher.OutboxPublisher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class AppointmentBatchCancelNotifyDecorator implements AppointmentBatchCancelService {

    private final AppointmentBatchCancelService delegate;
    private final AppointmentMapper mapper;
    private final ApplicationEventPublisher eventPublisher;
    private final OutboxPublisher publisher;

    public AppointmentBatchCancelNotifyDecorator(@Qualifier("appointmentBatchCancelDeferDecorator") AppointmentBatchCancelService delegate,
                                                 AppointmentMapper mapper, ApplicationEventPublisher eventPublisher, OutboxPublisher publisher) {
        this.delegate = delegate;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
        this.publisher = publisher;
    }

    @Transactional
    @Override
    public BatchResponse<AppointmentResponseDto> cancelBatchByDate(UUID participantId, LocalDate date) {
        BatchResponse<AppointmentResponseDto> batch = delegate.cancelBatchByDate(participantId, date);
        // FIXME: need specific algorithm for preparing before sending
        publisher.publish("canceled-appointment-notification", batch.data());
        //eventPublisher.publishEvent(new InternalAppointmentCancelEvent(participantId, mapper.toSystemDtoList(batch.data())));
        return batch;
    }

    @Override
    public BatchResponse<AppointmentResponseDto> cancelBatch(UUID participantId) {
        BatchResponse<AppointmentResponseDto> batch = delegate.cancelBatch(participantId);
        publisher.publish("canceled-appointment-notification", batch.data());
        //eventPublisher.publishEvent(new InternalAppointmentCancelEvent(participantId, mapper.toSystemDtoList(batch.data())));
        return batch;
    }
}
