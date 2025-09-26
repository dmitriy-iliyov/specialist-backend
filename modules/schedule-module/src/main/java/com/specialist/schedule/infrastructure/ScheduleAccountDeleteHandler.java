package com.specialist.schedule.infrastructure;

import com.specialist.contracts.auth.AccountDeleteEvent;
import com.specialist.contracts.auth.AccountDeleteHandler;
import com.specialist.contracts.profile.ProfileType;
import com.specialist.schedule.appointment.services.AppointmentBatchCancelService;
import com.specialist.schedule.appointment_duration.AppointmentDurationService;
import com.specialist.schedule.interval.services.SystemIntervalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ScheduleAccountDeleteHandler implements AccountDeleteHandler {

    private final SystemIntervalService intervalService;
    private final AppointmentBatchCancelService appointmentService;
    private final AppointmentDurationService appointmentDurationService;

    public ScheduleAccountDeleteHandler(SystemIntervalService intervalService,
                                        @Qualifier("appointmentBatchCancelNotifyDecorator") AppointmentBatchCancelService appointmentService,
                                        AppointmentDurationService appointmentDurationService) {
        this.intervalService = intervalService;
        this.appointmentService = appointmentService;
        this.appointmentDurationService = appointmentDurationService;
    }

    @Transactional
    @Override
    public void handle(AccountDeleteEvent event) {
        ProfileType profileType = ProfileType.fromStringRole(event.stringRole());
        if (profileType.equals(ProfileType.SPECIALIST)) {
            intervalService.deleteAllBySpecialistId(event.accountId());
            appointmentDurationService.deleteBySpecialistId(event.accountId());
        }
        appointmentService.cancelBatch(event.accountId());
    }
}
