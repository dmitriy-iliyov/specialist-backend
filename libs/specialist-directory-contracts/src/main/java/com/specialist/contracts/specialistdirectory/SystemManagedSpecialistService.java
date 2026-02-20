package com.specialist.contracts.specialistdirectory;

import com.specialist.contracts.specialistdirectory.dto.ExternalManagedSpecialistResponseDto;
import com.specialist.utils.pagination.PageDataHolder;

import java.util.List;
import java.util.UUID;

public interface SystemManagedSpecialistService {
    ExternalManagedSpecialistResponseDto findById(UUID id);
    List<ExternalManagedSpecialistResponseDto> findAll(PageDataHolder page);
}
