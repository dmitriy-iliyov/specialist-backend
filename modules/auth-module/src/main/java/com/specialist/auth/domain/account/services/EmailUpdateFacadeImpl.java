package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.models.EmailUpdatedEvent;
import com.specialist.auth.domain.account.models.dtos.AccountEmailUpdateDto;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.contracts.user.SystemUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailUpdateFacadeImpl implements EmailUpdateFacade {

    private final AccountService accountService;
    private final SystemUserService systemUserService;
    private final ApplicationEventPublisher eventPublisher;

    // WARNING: till systemUserService in the same app context
    @Transactional
    @Override
    public ShortAccountResponseDto updateEmail(AccountEmailUpdateDto dto) {
        ShortAccountResponseDto responseDto = accountService.updateEmail(dto);
        systemUserService.updateEmailById(dto.getId(), dto.getEmail());
        eventPublisher.publishEvent(new EmailUpdatedEvent(dto.getEmail()));
        return responseDto;
    }
}
