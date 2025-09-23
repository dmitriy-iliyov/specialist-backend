package com.specialist.schedule.infrastructure;

import com.specialist.contracts.auth.AccountDeleteEvent;
import com.specialist.contracts.auth.AccountDeleteHandler;
import com.specialist.contracts.profile.ProfileType;
import com.specialist.schedule.appointment.services.SystemAppointmentService;
import com.specialist.schedule.appointment_duration.AppointmentDurationService;
import com.specialist.schedule.interval.services.SystemIntervalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleAccountDeleteHandler implements AccountDeleteHandler {

    private final SystemIntervalService intervalService;
    private final SystemAppointmentService appointmentService;
    private final AppointmentDurationService appointmentDurationService;

    @Transactional
    @Override
    public void handle(AccountDeleteEvent event) {
        ProfileType profileType = ProfileType.fromStringRole(event.stringRole());
        if (profileType.equals(ProfileType.SPECIALIST)) {
            intervalService.deleteAllBySpecialistId(event.accountId());
            appointmentDurationService.deleteBySpecialistId(event.accountId());
        }
        // оповещение об отмене
        appointmentService.deleteAll(event.accountId());
    }
}
