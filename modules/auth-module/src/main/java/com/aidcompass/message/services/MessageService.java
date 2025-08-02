package com.aidcompass.message.services;

import com.aidcompass.message.models.MessageDto;

public interface MessageService {
    void sendMessage(MessageDto message) throws Exception;
}
