package com.messageservice.services;

import com.messageservice.models.MessageDto;

public interface MessageService {
    void sendMessage(MessageDto message) throws Exception;
}
