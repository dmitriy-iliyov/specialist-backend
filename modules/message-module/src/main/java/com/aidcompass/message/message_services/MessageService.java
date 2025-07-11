package com.aidcompass.message.message_services;

import com.aidcompass.message.message_services.models.MessageDto;

public interface MessageService {

    void sendMessage(MessageDto messageDto) throws Exception;

}
