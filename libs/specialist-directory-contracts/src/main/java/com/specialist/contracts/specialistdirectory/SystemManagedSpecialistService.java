package com.specialist.contracts.specialistdirectory;

import java.util.UUID;

public interface SystemManagedSpecialistService {
    ManagedSpecialistResponseDto findById(UUID id);
}
