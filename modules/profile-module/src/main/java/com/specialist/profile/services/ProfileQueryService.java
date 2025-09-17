package com.specialist.profile.services;

import com.specialist.profile.models.ProfileFilter;
import com.specialist.utils.pagination.PageResponse;

public interface ProfileQueryService {
    PageResponse<?> findAll(ProfileFilter filter);
}
