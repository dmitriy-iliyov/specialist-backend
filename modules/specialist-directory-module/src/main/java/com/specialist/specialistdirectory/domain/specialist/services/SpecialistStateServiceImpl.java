package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecialistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialistStateServiceImpl implements SpecialistStateService {

    private final SpecialistRepository repository;

    @Transactional
    @Override
    public void manage(UUID id, UUID ownerId) {
        repository.updateStateAndOwnerIdById(id, ownerId, SpecialistState.MANAGED);
    }

    @Transactional
    @Override
    public void recall(UUID id) {
        repository.updateStateById(id, SpecialistState.RECALLED);
    }
}
