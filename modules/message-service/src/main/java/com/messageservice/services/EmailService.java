package com.specialist.message.service.services;

import com.specialist.message.service.models.MessageDto;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService implements MessageService {

    private final JavaMailSender javaMailSender;

    @Async
    @Override
    public void sendMessage(MessageDto message) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        mimeMessageHelper.setTo(message.recipient());
        mimeMessageHelper.setSubject(message.subject());
        mimeMessageHelper.setText(message.text());
        javaMailSender.send(mimeMessage);
    }
}
