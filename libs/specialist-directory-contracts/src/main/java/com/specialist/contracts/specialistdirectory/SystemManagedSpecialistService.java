package com.specialist.contracts.specialistdirectory;

import com.specialist.contracts.specialistdirectory.dto.ManagedSpecialistResponseDto;
import com.specialist.utils.pagination.PageDataHolder;

import java.util.List;
import java.util.UUID;

public interface SystemManagedSpecialistService {
    ManagedSpecialistResponseDto findById(UUID id);
    List<ManagedSpecialistResponseDto> findAll(PageDataHolder page);
}
