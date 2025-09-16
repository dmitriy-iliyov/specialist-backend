package com.specialist.message.service.models;

public record MessageDto(
        String recipient,
        String subject,
        String text
) { }