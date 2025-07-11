package com.aidcompass.users.jurist.models.dto;


import com.aidcompass.users.detail.models.PrivateDetailResponseDto;

public record FullPrivateJuristResponseDto(
    PrivateJuristResponseDto jurist,

    PrivateDetailResponseDto detail
) { }
