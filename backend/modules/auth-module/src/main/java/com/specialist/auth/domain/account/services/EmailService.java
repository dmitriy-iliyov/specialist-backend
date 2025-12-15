package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.models.dtos.AccountEmailUpdateDto;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.account.models.events.EmailUpdatedEvent;
import com.specialist.contracts.profile.ProfileEmailUpdateService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmailUpdateFacadeImpl implements EmailUpdateFacade {

    private final AccountService accountService;
    private final ProfileEmailUpdateService profileEmailUpdateService;
    private final ApplicationEventPublisher eventPublisher;

    // WARNING: till systemUserService in the same app context
    @Transactional
    @Override
    public ShortAccountResponseDto updateEmail(AccountEmailUpdateDto dto) {
        if (dto.getId().equals(accountService.findIdByEmail(dto.getEmail()))) {}
        if (accountService.existsByEmail(dto.getEmail())) {

        }
        ShortAccountResponseDto responseDto = accountService.updateEmail(dto);
        profileEmailUpdateService.updateById(dto.getType(), dto.getId(), dto.getEmail());
        eventPublisher.publishEvent(new EmailUpdatedEvent(dto.getEmail()));
        return responseDto;
    }
}
