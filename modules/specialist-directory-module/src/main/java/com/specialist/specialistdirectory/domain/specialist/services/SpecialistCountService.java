package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.filters.AdminSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;

import java.util.UUID;

public interface SpecialistCountService {
    long countAll();

    long countByFilter(SpecialistFilter filter);

    long countByCreatorId(UUID creatorId);

    long countByCreatorIdAndFilter(UUID creatorId, ExtendedSpecialistFilter filter);

    long countByAdminFilter(AdminSpecialistFilter filter);
}
