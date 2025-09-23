package com.specialist.profile.services;

import com.specialist.profile.models.ProfileFilter;
import com.specialist.profile.models.enums.ScopeType;
import com.specialist.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Prefix User means that this service supply Public dtos
 */

@Service
@RequiredArgsConstructor
public class UserProfileQueryService implements ProfileQueryService {

    private final ProfileReadOrchestrator readOrchestrator;

    @Override
    public PageResponse<?> findAll(ProfileFilter filter) {
        return readOrchestrator.findAll(ScopeType.PUBLIC, filter);
    }
}
