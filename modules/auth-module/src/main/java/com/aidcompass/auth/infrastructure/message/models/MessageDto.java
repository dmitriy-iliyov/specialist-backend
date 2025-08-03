package com.aidcompass.auth.infrastructure.message.models;

public record MessageDto(
        String recipient,
        String subject,
        String text
) { }