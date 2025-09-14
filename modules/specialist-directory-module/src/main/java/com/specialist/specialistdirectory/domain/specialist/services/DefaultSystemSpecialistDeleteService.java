package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.contracts.specialistdirectory.SystemSpecialistDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultSystemSpecialistDeleteService implements SystemSpecialistDeleteService {

    private final SpecialistService service;

    @Override
    public void deleteById(UUID id) {
        service.deleteById(id);
    }
}
