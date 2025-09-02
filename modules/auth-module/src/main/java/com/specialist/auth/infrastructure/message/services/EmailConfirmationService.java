package com.specialist.auth.infrastructure.message.services;

import com.specialist.auth.domain.account.services.AccountConfirmationService;
import com.specialist.auth.domain.account.services.AccountService;
import com.specialist.auth.exceptions.AccountNotFoundByEmailException;
import com.specialist.auth.exceptions.CodeExpiredException;
import com.specialist.auth.exceptions.SendMessageException;
import com.specialist.auth.infrastructure.message.configs.MessageConfig;
import com.specialist.auth.infrastructure.message.models.ConfirmationEntity;
import com.specialist.auth.infrastructure.message.models.MessageDto;
import com.specialist.auth.infrastructure.message.repositories.ConfirmationRepository;
import com.specialist.utils.CodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailConfirmationService implements ConfirmationService {

    @Value("${api.conf.account.code.ttl.secs}")
    private Long CODE_TTL;
    private final MessageService messageService;
    private final ConfirmationRepository repository;
    private final AccountService accountService;
    private final AccountConfirmationService confirmationService;

    public EmailConfirmationService(@Qualifier("emailService") MessageService messageService,
                                    ConfirmationRepository repository, AccountService accountService,
                                    AccountConfirmationService confirmationService) {
        this.messageService = messageService;
        this.repository = repository;
        this.accountService = accountService;
        this.confirmationService = confirmationService;
    }

    @Async
    @Override
    public void sendConfirmationCode(String email) {
        if (!accountService.existsByEmail(email)) {
            throw new AccountNotFoundByEmailException();
        }
        try {
            String code = CodeGenerator.generate();
            repository.save(new ConfirmationEntity(code, email, CODE_TTL));
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
        confirmationService.confirmEmail(email);
        repository.deleteById(code);
        return email;
    }
}
