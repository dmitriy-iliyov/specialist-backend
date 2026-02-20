package com.messageservice.models;

public record MessageDto(
        String recipient,
        String subject,
        String text
) { }