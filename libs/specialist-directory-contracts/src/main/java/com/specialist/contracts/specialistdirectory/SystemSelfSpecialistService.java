package com.specialist.contracts.specialistdirectory;

import com.specialist.contracts.specialistdirectory.dto.ManagedSpecialistResponseDto;

import java.util.UUID;

public interface SystemSelfSpecialistService {
    ManagedSpecialistResponseDto findById(UUID id);
}
