package com.specialist.specialistdirectory.domain.specialist.services.specialist;

import com.specialist.contracts.specialistdirectory.SystemSelfSpecialistService;
import com.specialist.contracts.specialistdirectory.dto.ManagedSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.mappers.ManagedSpecialistMapper;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SystemSelfSpecialistServiceImpl implements SystemSelfSpecialistService {

    private final SpecialistService service;
    private final ManagedSpecialistMapper mapper;

    @Override
    public ManagedSpecialistResponseDto findById(UUID id) {
        return mapper.toManagedDto(service.findById(id));
    }
}
