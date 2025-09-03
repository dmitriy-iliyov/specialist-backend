package com.specialist.auth.infrastructure.message.services;

import com.specialist.auth.core.AccountLoginOrchestrator;
import com.specialist.auth.core.models.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationFacadeImpl implements ConfirmationFacade {

    private final ConfirmationService confirmationService;
    private final AccountLoginOrchestrator accountLoginOrchestrator;

    public ConfirmationFacadeImpl(
            ConfirmationService confirmationService,
            @Qualifier("postConfirmationAccountLoginOrchestrator") AccountLoginOrchestrator accountLoginOrchestrator) {
        this.confirmationService = confirmationService;
        this.accountLoginOrchestrator = accountLoginOrchestrator;
    }

    @Override
    public void confirm(String code, HttpServletRequest request, HttpServletResponse response) {
        String email = confirmationService.confirmEmailByCode(code);
        accountLoginOrchestrator.login(new LoginRequest(email, null), request, response);
    }
}
