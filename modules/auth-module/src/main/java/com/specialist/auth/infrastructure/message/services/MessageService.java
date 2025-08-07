package com.specialist.auth.infrastructure.message.services;

import com.specialist.auth.infrastructure.message.models.MessageDto;

public interface MessageService {
    void sendMessage(MessageDto message) throws Exception;
}
