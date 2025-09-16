package com.specialist.core.exceptions.models.dto;

public record ExceptionResponseDto(
        String code,
        String message,
        String description
) { }
