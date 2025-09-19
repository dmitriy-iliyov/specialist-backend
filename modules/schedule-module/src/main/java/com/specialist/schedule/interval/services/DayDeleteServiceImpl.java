package com.specialist.schedule.interval.services;

import com.specialist.schedule.appointment.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DayDeleteServiceImpl implements DayDeleteService {

    private final IntervalService intervalService;
    private final AppointmentService appointmentService;

    @Transactional
    @Override
    public void deleteAllBySpecialistIdAndDate(UUID specialistId, LocalDate date) {
        intervalService.deleteAllBySpecialistIdAndDate(specialistId, date);
        appointmentService.cancelAllByDate(specialistId, date);
    }
}
