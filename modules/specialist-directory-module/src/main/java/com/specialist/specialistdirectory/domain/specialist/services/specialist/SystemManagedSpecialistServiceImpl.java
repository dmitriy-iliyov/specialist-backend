package com.specialist.specialistdirectory.domain.specialist.services.specialist;

import com.specialist.contracts.specialistdirectory.SystemManagedSpecialistService;
import com.specialist.contracts.specialistdirectory.dto.ManagedSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.mappers.ManagedSpecialistMapper;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
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
    public ManagedSpecialistResponseDto findById(UUID id) {
        SpecialistResponseDto dto = service.findByIdAndStatus(id, SpecialistStatus.MANAGED);
        return mapper.toManagedDto(dto);
    }

    @Override
    public List<ManagedSpecialistResponseDto> findAll(PageDataHolder page) {
        List<SpecialistResponseDto> dtoList = service.findAll(new SystemSpecialistFilter(page, SpecialistStatus.MANAGED));
        return mapper.toManagedDtoList(dtoList);
    }
}
