package com.specialist.contracts.specialistdirectory;

import java.util.UUID;

public interface SystemSelfSpecialistService {
    ManagedSpecialistResponseDto findById(UUID id);
}
