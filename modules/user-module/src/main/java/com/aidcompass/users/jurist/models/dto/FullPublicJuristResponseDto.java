package com.aidcompass.users.jurist.models.dto;

import com.aidcompass.users.detail.models.PublicDetailResponseDto;

public record FullPublicJuristResponseDto(
    PublicJuristResponseDto jurist,

    PublicDetailResponseDto detail
) { }
