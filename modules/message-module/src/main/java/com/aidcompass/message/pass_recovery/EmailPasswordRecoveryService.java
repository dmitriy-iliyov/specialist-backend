package com.aidcompass.message.pass_recovery;


import com.aidcompass.message.exceptions.models.InvalidPasswordRecoveryTokenException;
import com.aidcompass.message.message_services.MessageService;
import com.aidcompass.message.message_services.MessageFactory;
import com.aidcompass.core.security.auth.dto.RecoveryRequestDto;
import com.aidcompass.core.security.domain.user.services.UserOrchestrator;
import com.aidcompass.message.utils.CodeFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class EmailPasswordRecoveryService implements PasswordRecoveryService {

    private final MessageService messageService;
    private final PasswordRecoveryRepository passwordRecoveryRepository;
    private final UserOrchestrator userOrchestrator;


    public EmailPasswordRecoveryService(@Qualifier("emailMessageService") MessageService messageService,
                                        PasswordRecoveryRepository passwordRecoveryRepository,
                                        UserOrchestrator userOrchestrator) {
        this.messageService = messageService;
        this.passwordRecoveryRepository = passwordRecoveryRepository;
        this.userOrchestrator = userOrchestrator;
    }

    @Override
    public void sendRecoveryMessage(String recipientResource) throws Exception {
        String code = CodeFactory.generate();
        messageService.sendMessage(MessageFactory.passwordRecovery(recipientResource, code));
        passwordRecoveryRepository.save(code, recipientResource);
    }

    @Override
    public void recoverPassword(String code, String password) {
        String email = passwordRecoveryRepository.findAndDeleteByToken(code).orElseThrow(
                InvalidPasswordRecoveryTokenException::new
        );
        userOrchestrator.recoverPasswordByEmail(new RecoveryRequestDto(email, password));
    }
}
