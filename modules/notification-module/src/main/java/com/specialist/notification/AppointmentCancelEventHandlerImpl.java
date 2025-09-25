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
        Set<UUID> ids = new HashSet<>();
        appointments.forEach(appointment -> ids.addAll(Set.of(appointment.specialistId(), appointment.userId())));
        Map<UUID, SystemShortProfileResponseDto> profiles = profileService.findAllShortByIdIn(ids);
        eventSender.sendEvents(
                appointments.stream()
                        .map(appointment -> new ExternalAppointmentCancelEvent(
                                appointment, profiles.get(appointment.userId()), profiles.get(appointment.specialistId()))
                        )
                        .toList()
        );
    }
}
