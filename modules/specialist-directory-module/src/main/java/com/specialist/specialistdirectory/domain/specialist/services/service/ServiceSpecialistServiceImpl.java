package com.specialist.specialistdirectory.domain.specialist.services.service;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceSpecialistServiceImpl implements ServiceSpecialistService {

    private final SpecialistService service;

    @Override
    public SpecialistResponseDto save(UUID creatorId, SpecialistCreateDto dto) {
        dto.setCreatorId(creatorId);
        dto.setCreatorType(CreatorType.SERVICE);
        dto.setStatus(SpecialistStatus.APPROVED);
        dto.setState(SpecialistState.DEFAULT);
        return service.save(dto);
    }
}
