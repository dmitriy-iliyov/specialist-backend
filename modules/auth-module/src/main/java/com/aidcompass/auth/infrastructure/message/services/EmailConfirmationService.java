package com.aidcompass.auth.infrastructure.message.services;

import com.aidcompass.auth.domain.account.services.AccountService;
import com.aidcompass.auth.exceptions.AccountNotFoundByEmailException;
import com.aidcompass.auth.exceptions.CodeExpiredException;
import com.aidcompass.auth.exceptions.SendMessageException;
import com.aidcompass.auth.infrastructure.message.CodeGenerator;
import com.aidcompass.auth.infrastructure.message.configs.MessageConfig;
import com.aidcompass.auth.infrastructure.message.models.ConfirmationEntity;
import com.aidcompass.auth.infrastructure.message.models.MessageDto;
import com.aidcompass.auth.infrastructure.message.repositories.ConfirmationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
public class EmailConfirmationService implements ConfirmationService {

    @Value("${api.conf.account.token.ttl.secs}")
    private Long CODE_TTL;
    private final MessageService messageService;
    private final ConfirmationRepository repository;
    private final AccountService accountService;

    public EmailConfirmationService(@Qualifier("emailService") MessageService messageService,
                                    ConfirmationRepository repository, AccountService accountService) {
        this.messageService = messageService;
        this.repository = repository;
        this.accountService = accountService;
    }

    @Override
    public void sendConfirmationCode(String email) {
        if (!accountService.existsByEmail(email)) {
            throw new AccountNotFoundByEmailException();
        }
        try {
            String code = CodeGenerator.generate();
            repository.save(new ConfirmationEntity(code, email, Duration.ofSeconds(CODE_TTL)));
            messageService.sendMessage(
                    new MessageDto(email, "Account confirmation", MessageConfig.ACCOUNT_CONFIRMATION.formatted(code))
            );
        } catch (Exception e) {
            log.error("Failed send confirmation message: {}", e.getMessage());
            throw new SendMessageException();
        }
    }

    @Override
    public String confirmEmailByCode(String code) {
        ConfirmationEntity entity = repository.findById(code).orElse(null);
        if (entity == null) {
            throw new CodeExpiredException();
        }
        String email = entity.email();
        accountService.confirmEmail(email);
        repository.deleteById(code);
        return email;
    }
}
