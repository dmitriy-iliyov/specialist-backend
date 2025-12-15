package com.specialist.schedule.infrastructure;

import com.specialist.contracts.auth.ImmediatelyAccountDeleteEvent;
import com.specialist.contracts.auth.ImmediatelyAccountDeleteHandler;
import com.specialist.contracts.profile.ProfileType;
import com.specialist.schedule.appointment.services.AppointmentBatchCancelService;
import com.specialist.schedule.appointment_duration.AppointmentDurationService;
import com.specialist.schedule.interval.services.SystemIntervalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScheduleAccountDeleteHandler implements ImmediatelyAccountDeleteHandler {

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
    public void handle(List<ImmediatelyAccountDeleteEvent> events) {
        Set<UUID> specialistAccountIds = new HashSet<>();
        Set<UUID> accountIds = events.stream()
                .map(event -> {
                    if (ProfileType.fromStringRole(event.stringRole()).equals(ProfileType.SPECIALIST)) {
                        specialistAccountIds.add(event.accountId());
                    }
                    return event.accountId();
                })
                .collect(Collectors.toSet());
        if (!specialistAccountIds.isEmpty()) {
            intervalService.deleteAllFutureBySpecialistIds(specialistAccountIds);
            appointmentDurationService.deleteAllBySpecialistIds(specialistAccountIds);
        }
        appointmentService.cancelBatch(accountIds);
    }
}
