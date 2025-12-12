package com.specialist.notification;

import com.specialist.contracts.notification.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentCancelEventHandlerImpl implements AppointmentCancelEventHandler {

    private final AppointmentCancelEventSender eventSender;
    private final SystemShortProfileService profileService;

    @Override
    public void handle(InternalAppointmentCancelEvent event) {
        Set<SystemAppointmentResponseDto> appointments = new HashSet<>(event.appointments());
        InitiatorType initiatorType = null;
        Set<UUID> ids = new HashSet<>();
        for (SystemAppointmentResponseDto appointment : appointments) {
            int initiatorFlag;
            if (!appointment.userId().equals(event.initiatorId())) {
                ids.add(appointment.userId());
                initiatorFlag = 2;
            } else {
                ids.add(appointment.specialistId());
                initiatorFlag = 1;
            }
            if (initiatorType == null) {
                initiatorType = initiatorFlag % 2 == 0 ? InitiatorType.SPECIALIST : InitiatorType.USER;
            }
        }
        final InitiatorType finalInitiatorType = initiatorType;
        Map<UUID, SystemShortProfileResponseDto> profiles = profileService.findAllShortByIdIn(ids);
        eventSender.sendEvents(
                appointments.stream()
                        .map(appointment -> {
                            if (finalInitiatorType.equals(InitiatorType.USER)) {
                                return new ExternalAppointmentCancelEvent(finalInitiatorType, appointment, profiles.get(appointment.specialistId()));
                            }
                            return new ExternalAppointmentCancelEvent(finalInitiatorType, appointment, profiles.get(appointment.userId()));
                        })
                        .toList()
        );
        // outbox
    }
}
