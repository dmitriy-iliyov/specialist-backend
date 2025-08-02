package com.aidcompass.message.services;

import com.aidcompass.message.CodeGenerator;
import com.aidcompass.message.configs.MessageConfig;
import com.aidcompass.message.exceptions.SendMessageException;
import com.aidcompass.message.models.MessageDto;
import com.aidcompass.message.repositories.ConfirmationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailConfirmationService implements ConfirmationService, ValidateService {

    private final MessageService messageService;
    private final ConfirmationRepository repository;

    public EmailConfirmationService(@Qualifier("emailService") MessageService messageService,
                                    ConfirmationRepository repository) {
        this.messageService = messageService;
        this.repository = repository;
    }

    @Override
    public void sendConfirmationMessage(UUID accountId, String email) {
        String code = CodeGenerator.generate();
        try {
            repository.save(code, accountId);
            messageService.sendMessage(
                    new MessageDto(email, "Account confirmation", MessageConfig.ACCOUNT_CONFIRMATION.formatted(code))
            );
        } catch (Exception e) {
            throw new SendMessageException();
        }
    }

    @Override
    public void validateCode(String code) {

    }
}
