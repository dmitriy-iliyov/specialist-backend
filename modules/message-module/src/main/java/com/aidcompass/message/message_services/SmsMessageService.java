package com.aidcompass.message.message_services;

import com.aidcompass.message.exceptions.models.SmsSendException;
import com.aidcompass.message.message_services.models.MessageDto;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class SmsMessageService implements MessageService {

    @Value("${message-service.phone}")
    private String companyPhoneNumber;

    @Value("${message-service.twilio.account-sid}")
    private String accountSid;

    @Value("${message-service.twilio.auth-token}")
    private String authToken;


    @PostConstruct
    public void setUpConnection() {
        Twilio.init(accountSid, authToken);
    }

    @Async
    @Override
    public void sendMessage(MessageDto messageDto) {
        Message message = Message.creator(
                new PhoneNumber(messageDto.recipient()),
                new PhoneNumber(companyPhoneNumber),
                messageDto.subject() + " " + messageDto.text()
        ).create();

        System.out.println(message.toString());
        if (Message.Status.QUEUED != message.getStatus()) {
            throw new SmsSendException();
        }
    }
}