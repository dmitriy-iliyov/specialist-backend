package com.aidcompass.users.jurist.models.dto;

import com.aidcompass.users.general.dto.BasePrivateVolunteerDto;
import com.aidcompass.users.jurist.specialization.models.JuristSpecialization;
import com.aidcompass.users.jurist.specialization.models.JuristType;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public record PrivateJuristResponseDto(

        @JsonUnwrapped
        BasePrivateVolunteerDto baseDto,

        JuristType type,

        List<JuristSpecialization> specializations

) { }
