package com.aidcompass.users.doctor.models.dto;


import com.aidcompass.users.detail.models.PrivateDetailResponseDto;

public record FullPrivateDoctorResponseDto(
    PrivateDoctorResponseDto doctor,

    PrivateDetailResponseDto detail
) { }
