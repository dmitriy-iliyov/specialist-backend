package com.specialist.user.models.dtos;

import com.specialist.contracts.user.BaseResponseDto;

import java.util.UUID;

public class PublicSpecialistResponseDto extends BaseResponseDto {
    public PublicSpecialistResponseDto(UUID id, String fullName) {
        super(id, fullName);
    }
}
