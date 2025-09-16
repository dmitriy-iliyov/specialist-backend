package com.specialist.message.service.services;

import com.specialist.message.service.models.MessageDto;

public interface MessageService {
    void sendMessage(MessageDto message) throws Exception;
}
