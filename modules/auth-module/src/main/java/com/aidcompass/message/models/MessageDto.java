package com.aidcompass.message.models;

public record MessageDto(
        String recipient,
        String subject,
        String text
) { }