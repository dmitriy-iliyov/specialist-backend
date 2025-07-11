package com.aidcompass.aggregator.api.appointment.dto;

import com.aidcompass.schedule.appointment.models.dto.AppointmentResponseDto;
import com.aidcompass.core.contact.core.models.dto.PublicContactResponseDto;
import com.aidcompass.users.customer.models.PublicCustomerResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record VolunteerAppointmentDto(
        AppointmentResponseDto appointment,
        @JsonProperty("avatar_url")
        String avatarUrl,
        PublicCustomerResponseDto customer,
        List<PublicContactResponseDto> contacts
) { }
