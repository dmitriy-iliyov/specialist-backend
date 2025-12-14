package com.specialist.specialistdirectory.infrastructure;

import com.specialist.contracts.auth.AccountDeleteEvent;
import com.specialist.contracts.auth.AccountDeleteHandler;
import com.specialist.contracts.profile.ProfileType;
import com.specialist.specialistdirectory.domain.bookmark.services.BookmarkService;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecialistDirectoryAccountDeleteHandler implements AccountDeleteHandler {

    private final SpecialistService specialistService;
    private final BookmarkService bookmarkService;

    // DISCUSS schedule and if reviews count is big
    @Transactional
    @Override
    public void handle(List<AccountDeleteEvent> events) {
        Set<UUID> specialistAccountIds = new HashSet<>();
        Set<UUID> accountIds = events.stream()
                .map(event -> {
                    if (ProfileType.fromStringRole(event.stringRole()).equals(ProfileType.SPECIALIST)) {
                        specialistAccountIds.add(event.accountId());
                    }
                    return event.accountId();
                })
                .collect(Collectors.toSet());
        bookmarkService.deleteAllByOwnerIds(accountIds);
        if (!specialistAccountIds.isEmpty()) {
            specialistService.deleteAllByOwnerIds(specialistAccountIds);
        }
    }
}
