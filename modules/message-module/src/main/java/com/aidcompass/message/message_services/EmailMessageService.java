package com.aidcompass.message.message_services;

import com.aidcompass.message.message_services.models.MessageDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailMessageService implements MessageService {

    private final JavaMailSender mailSender;


    @Async
    @Override
    public void sendMessage(MessageDto messageDto) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo(messageDto.recipient());
        helper.setSubject(messageDto.subject());
        helper.setText(messageDto.text(), true);
        mailSender.send(mimeMessage);
    }

}
