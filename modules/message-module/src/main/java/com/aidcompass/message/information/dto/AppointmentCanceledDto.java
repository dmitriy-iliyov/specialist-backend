package com.aidcompass.message.information.dto;

public record AppointmentCanceledDto(
        UserDto customer,
        UserDto volunteer,
        AppointmentDto appointment
) { }
