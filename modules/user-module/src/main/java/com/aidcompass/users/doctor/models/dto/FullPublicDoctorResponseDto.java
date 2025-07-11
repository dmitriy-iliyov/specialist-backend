package com.aidcompass.users.doctor.models.dto;


import com.aidcompass.users.detail.models.PublicDetailResponseDto;

public record FullPublicDoctorResponseDto(
    PublicDoctorResponseDto doctor,

    PublicDetailResponseDto detail
) { }
