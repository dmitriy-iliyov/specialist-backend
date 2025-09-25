package com.specialist.specialistdirectory.domain.specialist.services.creator;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistCountService;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import com.specialist.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreatorSpecialistServiceImpl implements CreatorSpecialistService {

    private final SpecialistService service;
    private final SpecialistCountService countService;

    @Override
    public SpecialistResponseDto findById(UUID creatorId, UUID id) {
        return service.findByCreatorIdAndId(creatorId, id);
    }

    @Override
    public PageResponse<SpecialistResponseDto> findAllByFilter(UUID creatorId, ExtendedSpecialistFilter filter) {
        return service.findAllByCreatorIdAndFilter(creatorId, filter);
    }

    @Override
    public long count(UUID creatorId) {
        return countService.countByCreatorId(creatorId);
    }
}
