package com.specialist.specialistdirectory.infrastructure;

import com.specialist.contracts.auth.AccountDeleteEvent;
import com.specialist.contracts.auth.AccountDeleteHandler;
import com.specialist.contracts.profile.ProfileType;
import com.specialist.specialistdirectory.domain.bookmark.services.BookmarkService;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpecialistDirectoryAccountDeleteHandler implements AccountDeleteHandler {

    private final SpecialistService specialistService;
    private final BookmarkService bookmarkService;

    @Transactional
    @Override
    public void handle(AccountDeleteEvent event) {
        bookmarkService.deleteAllByOwnerId(event.accountId());
        if (ProfileType.fromStringRole(event.stringRole()).equals(ProfileType.SPECIALIST)) {
            specialistService.deleteByOwnerId(event.accountId());
        }
    }
}
