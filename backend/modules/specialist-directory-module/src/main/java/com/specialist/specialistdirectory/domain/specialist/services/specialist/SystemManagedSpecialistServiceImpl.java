package com.specialist.specialistdirectory.domain.specialist.services.specialist;

import com.specialist.contracts.specialistdirectory.SystemManagedSpecialistService;
import com.specialist.contracts.specialistdirectory.dto.ExternalManagedSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.mappers.ManagedSpecialistMapper;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import com.specialist.specialistdirectory.domain.specialist.models.filters.SystemSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.services.SystemSpecialistService;
import com.specialist.utils.pagination.PageDataHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SystemManagedSpecialistServiceImpl implements SystemManagedSpecialistService {

    private final SystemSpecialistService service;
    private final ManagedSpecialistMapper mapper;

    @Override
    public ExternalManagedSpecialistResponseDto findById(UUID id) {
        SpecialistResponseDto dto = service.findByIdAndState(id, SpecialistState.MANAGED);
        return mapper.toExternalManagedDto(dto);
    }

    @Override
    public List<ExternalManagedSpecialistResponseDto> findAll(PageDataHolder page) {
        List<SpecialistResponseDto> dtoList = service.findAll(new SystemSpecialistFilter(page, null, SpecialistState.MANAGED));
        return mapper.toExternalManagedDtoList(dtoList);
    }
}
