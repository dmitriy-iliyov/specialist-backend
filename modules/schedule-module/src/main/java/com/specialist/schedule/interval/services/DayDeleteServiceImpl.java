package com.specialist.schedule.interval.services;

import com.specialist.schedule.appointment.services.AppointmentBatchCancelService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class DayDeleteServiceImpl implements DayDeleteService {

    private final IntervalService intervalService;
    private final AppointmentBatchCancelService appointmentService;

    public DayDeleteServiceImpl(IntervalService intervalService,
                                @Qualifier("appointmentBatchCancelNotifyDecorator") AppointmentBatchCancelService appointmentService) {
        this.intervalService = intervalService;
        this.appointmentService = appointmentService;
    }

    @Transactional
    @Override
    public void deleteAllBySpecialistIdAndDate(UUID specialistId, LocalDate date) {
        intervalService.deleteAllBySpecialistIdAndDate(specialistId, date);
        appointmentService.cancelBatchByDate(specialistId, date);
    }
}
