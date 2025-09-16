package com.specialist.message;

import com.specialist.message.core.MessageDto;
import com.specialist.message.core.MessageService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseEmailServiceDecorator implements MessageService {

    private final MessageService messageService;

    @Override
    public void sendMessage(MessageDto message) throws Exception {
        messageService.sendMessage(message);
    }
}
