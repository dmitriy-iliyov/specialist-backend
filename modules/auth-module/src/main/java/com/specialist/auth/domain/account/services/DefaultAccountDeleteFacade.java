package com.specialist.auth.domain.account.services;

import com.specialist.contracts.profile.ProfileDeleteOrchestrator;
import com.specialist.contracts.profile.ProfileType;
import com.specialist.contracts.specialistdirectory.SystemBookmarkDeleteService;
import com.specialist.contracts.specialistdirectory.SystemSpecialistDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultAccountDeleteFacade implements AccountDeleteFacade {

    private final AccountService accountService;
    private final ProfileDeleteOrchestrator profileDeleteOrchestrator;
    private final SystemBookmarkDeleteService bookmarkDeleteService;
    private final SystemSpecialistDeleteService specialistDeleteService;

    // TODO schedule delete / kafka event

    // WARNING: till in the same app context
    @Transactional
    @Override
    public void delete(UUID id) {
        ProfileType type = ProfileType.fromStringRole(accountService.findRoleById(id).toString());
        accountService.deleteById(id);
        profileDeleteOrchestrator.delete(id, type);
        if (type.equals(ProfileType.SPECIALIST)) {
            specialistDeleteService.deleteById(id);
        }
        bookmarkDeleteService.deleteAllByOwnerId(id);
    }
}
