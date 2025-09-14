package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.filters.AdminSpecialistFilter;
import com.specialist.utils.pagination.PageResponse;

public interface AdminSpecialistQueryOrchestrator {
    PageResponse<?> findAll(AdminSpecialistFilter filter);
}
