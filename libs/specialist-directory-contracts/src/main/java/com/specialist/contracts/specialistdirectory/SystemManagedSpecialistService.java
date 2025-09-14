package com.specialist.contracts.specialistdirectory;

import java.util.UUID;

public interface ManagedSpecialistService {
    ManagedSpecialistResponseDto findById(UUID id);
}
