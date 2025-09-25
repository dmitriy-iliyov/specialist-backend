package com.specialist.schedule.appointment.services;

import com.specialist.schedule.appointment.models.dto.AppointmentCancelTaskDto;
import com.specialist.schedule.appointment.models.enums.AppointmentTaskType;
import com.specialist.schedule.appointment.repositories.AppointmentCancelTaskRepository;
import com.specialist.schedule.interval.services.IntervalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class AppointmentDeferCanselServiceImpl implements AppointmentDeferCanselService {

    private final AppointmentCancelService appointmentService;
    private final AppointmentCancelTaskService taskService;

    public AppointmentDeferCanselServiceImpl(@Qualifier("appointmentCancelNotifyDecorator") AppointmentCancelService appointmentService,
                                             AppointmentCancelTaskService taskService) {
        this.appointmentService = appointmentService;
        this.taskService = taskService;
    }

    @Transactional
    @Override
    public void cancel() {
        AppointmentCancelTaskDto dto = taskService.findByDate
    }
}
