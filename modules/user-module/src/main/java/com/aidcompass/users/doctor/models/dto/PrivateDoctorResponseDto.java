package com.aidcompass.users.doctor.models.dto;

import com.aidcompass.users.doctor.specialization.models.DoctorSpecialization;
import com.aidcompass.users.general.dto.BasePrivateVolunteerDto;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public record PrivateDoctorResponseDto(

        @JsonUnwrapped
        BasePrivateVolunteerDto baseDto,

        List<DoctorSpecialization> specializations
) { }
