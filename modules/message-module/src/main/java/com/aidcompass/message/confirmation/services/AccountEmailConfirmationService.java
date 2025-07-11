package com.aidcompass.message.confirmation.services;


import com.aidcompass.contracts.AccountResourceConfirmationService;
import com.aidcompass.message.confirmation.repositories.ConfirmationRepository;
import com.aidcompass.message.exceptions.models.InvalidConfirmationTokenException;
import com.aidcompass.message.exceptions.models.SendConfirmationMessageException;
import com.aidcompass.message.message_services.MessageFactory;
import com.aidcompass.message.message_services.MessageService;
import com.aidcompass.core.security.domain.user.services.UserOrchestrator;
import com.aidcompass.message.utils.CodeFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.aidcompass.core.general.GlobalRedisConfig.CONF_TOKEN_KEY_TEMPLATE;


@Service
public class AccountEmailConfirmationService implements AccountResourceConfirmationService {

    private final MessageService messageService;
    private final UserOrchestrator userOrchestrator;


    @Value("${api.conf.account.rsrc.token.ttl.secs}")
    private Long TOKEN_TTL;
    private final String KEY_TEMPLATE = CONF_TOKEN_KEY_TEMPLATE + "account_email:%s";
    private final ConfirmationRepository confirmationRepository;


    public AccountEmailConfirmationService(@Qualifier("emailMessageService") MessageService messageService,
                                           UserOrchestrator userOrchestrator,
                                           ConfirmationRepository confirmationRepository) {
        this.messageService = messageService;
        this.userOrchestrator = userOrchestrator;
        this.confirmationRepository = confirmationRepository;
    }

    @Override
    public void sendConfirmationMessage(String resource) {
        String code = CodeFactory.generate();
        confirmationRepository.save(KEY_TEMPLATE.formatted(code), resource, TOKEN_TTL);
        try {
            messageService.sendMessage(MessageFactory.accountConfirmation(resource,code));
        } catch (Exception e) {
            throw new SendConfirmationMessageException();
        }
    }

    @Override
    public void validateConfirmationToken(String token) {
        String email = confirmationRepository.findAndDeleteByToken(KEY_TEMPLATE.formatted(token)).orElseThrow(
                InvalidConfirmationTokenException::new
        );
        userOrchestrator.confirmByEmail(email);
    }
}
