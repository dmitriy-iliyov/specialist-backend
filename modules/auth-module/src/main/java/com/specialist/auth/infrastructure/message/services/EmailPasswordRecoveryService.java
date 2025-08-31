package com.specialist.auth.infrastructure.message.services;

import com.specialist.auth.domain.account.services.AccountService;
import com.specialist.auth.exceptions.AccountNotFoundByEmailException;
import com.specialist.auth.exceptions.CodeExpiredException;
import com.specialist.auth.exceptions.SendMessageException;
import com.specialist.utils.CodeGenerator;
import com.specialist.auth.infrastructure.message.configs.MessageConfig;
import com.specialist.auth.infrastructure.message.models.MessageDto;
import com.specialist.auth.infrastructure.message.models.PasswordRecoveryEntity;
import com.specialist.auth.infrastructure.message.models.PasswordRecoveryRequest;
import com.specialist.auth.infrastructure.message.repositories.PasswordRecoveryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailPasswordRecoveryService implements PasswordRecoveryService {

    @Value("${api.account.pass-recovery.code.ttl.secs}")
    public Long CODE_TTL;
    private final MessageService messageService;
    private final PasswordRecoveryRepository repository;
    private final AccountService accountService;

    public EmailPasswordRecoveryService(@Qualifier("emailService") MessageService messageService,
                                        PasswordRecoveryRepository repository, AccountService accountService) {
        this.messageService = messageService;
        this.repository = repository;
        this.accountService = accountService;
    }

    @Override
    public void sendRecoveryCode(String recipientEmail) {
        if (!accountService.existsByEmail(recipientEmail)) {
            throw new AccountNotFoundByEmailException();
        }
        try {
            String code = CodeGenerator.generate();
            repository.save(new PasswordRecoveryEntity(code, recipientEmail, CODE_TTL));
            messageService.sendMessage(new MessageDto(recipientEmail, "Password recovery", MessageConfig.PASS_RECOVERY.formatted(code)));
        } catch (Exception e) {
            log.error("Failed send recovery message: {}", e.getMessage());
            throw new SendMessageException();
        }
    }

    @Override
    public void recoverPasswordByCode(PasswordRecoveryRequest request) {
        PasswordRecoveryEntity entity = repository.findById(request.code()).orElse(null);
        if (entity == null) {
            throw new CodeExpiredException();
        }
        accountService.recoverPasswordByEmail(entity.email(), request.password());
        repository.deleteById(request.code());
    }
}
