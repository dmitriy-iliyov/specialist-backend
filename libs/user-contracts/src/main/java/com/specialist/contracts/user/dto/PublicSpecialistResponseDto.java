package com.specialist.contracts.user.dto;

import java.util.UUID;

public class PublicSpecialistResponseDto extends BaseResponseDto {
    public PublicSpecialistResponseDto(UUID id, String fullName) {
        super(id, fullName);
    }
}
