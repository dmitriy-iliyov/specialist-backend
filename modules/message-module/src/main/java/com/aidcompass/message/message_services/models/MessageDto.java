package com.aidcompass.message.message_services.models;


public record MessageDto (
        String recipient,
        String subject,
        String text
) {}
