package com.specialist.message.core;

public record MessageDto(
        String recipient,
        String subject,
        String text
) { }