package com.aidcompass.message.information.dto;

public record AppointmentReminderDto(
     UserDto customer,
     AppointmentDto appointment
) { }