package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.models.dtos.AccountEmailUpdateDto;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.account.models.events.EmailUpdatedEvent;
import com.specialist.auth.exceptions.NonUniqueEmailException;
import com.specialist.contracts.auth.EmailReadService;
import com.specialist.contracts.profile.ProfileEmailUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailManagementService implements EmailUpdateService, EmailReadService {

    private final AccountService accountService;
    private final ProfileEmailUpdateService profileEmailUpdateService;
    private final ApplicationEventPublisher eventPublisher;

    // WARNING: till profileEmailUpdateService in the same app context
    @Transactional
    @Override
    public ShortAccountResponseDto update(AccountEmailUpdateDto dto) {
        if (accountService.existsByEmail(dto.getEmail())) {
            if (dto.getId().equals(accountService.findIdByEmail(dto.getEmail()))) {
                return new ShortAccountResponseDto(dto.getId(), dto.getEmail());
            } else {
                throw new NonUniqueEmailException();
            }
        }
        ShortAccountResponseDto responseDto = accountService.updateEmail(dto);
        profileEmailUpdateService.updateById(dto.getType(), dto.getId(), dto.getEmail());
        eventPublisher.publishEvent(new EmailUpdatedEvent(dto.getEmail()));
        return responseDto;
    }

    @Override
    public String findById(UUID id) {
        return accountService.findEmailById(id);
    }
}
