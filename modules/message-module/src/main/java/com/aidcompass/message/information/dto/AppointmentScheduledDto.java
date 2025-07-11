package com.aidcompass.message.information.dto;

public record AppointmentScheduledDto(
        UserDto customer,
        String volunteerType,
        UserDto volunteer,
        AppointmentDto appointment
) { }
