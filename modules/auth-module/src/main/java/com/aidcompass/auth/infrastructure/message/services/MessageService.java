package com.aidcompass.auth.infrastructure.message.services;

import com.aidcompass.auth.infrastructure.message.models.MessageDto;

public interface MessageService {
    void sendMessage(MessageDto message) throws Exception;
}
