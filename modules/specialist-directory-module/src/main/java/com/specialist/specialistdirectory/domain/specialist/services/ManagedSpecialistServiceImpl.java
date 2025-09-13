package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.contracts.specialistdirectory.ManagedSpecialistResponseDto;
import com.specialist.contracts.specialistdirectory.ManagedSpecialistService;
import com.specialist.specialistdirectory.domain.specialist.mappers.ManagedSpecialistMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManagedSpecialistServiceImpl implements ManagedSpecialistService {

    private final SpecialistService service;
    private final ManagedSpecialistMapper mapper;

    @Override
    public ManagedSpecialistResponseDto findById(UUID id) {
        return mapper.toManagedDto(service.findById(id));
    }
}
