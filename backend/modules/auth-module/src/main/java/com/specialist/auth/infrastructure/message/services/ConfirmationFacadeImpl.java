package com.specialist.auth.infrastructure.message.services;

import com.specialist.auth.core.web.AccountLoginService;
import com.specialist.auth.core.web.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationFacadeImpl implements ConfirmationFacade {

    private final ConfirmationService confirmationService;
    private final AccountLoginService loginService;

    public ConfirmationFacadeImpl(ConfirmationService confirmationService,
                                  @Qualifier("implicitAccountLoginService") AccountLoginService loginService) {
        this.confirmationService = confirmationService;
        this.loginService = loginService;
    }

    @Override
    public void confirm(String code, HttpServletRequest request, HttpServletResponse response) {
        String email = confirmationService.confirmEmailByCode(code);
        loginService.login(new LoginRequest(email, null), request, response);
    }
}
