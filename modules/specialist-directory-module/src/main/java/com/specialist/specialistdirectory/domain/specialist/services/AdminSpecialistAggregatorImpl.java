package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.contracts.user.PublicUserResponseDto;
import com.specialist.contracts.user.SystemUserService;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.FullSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistAggregatedResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.filters.AdminSpecialistFilter;
import com.specialist.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminSpecialistAggregatorImpl implements AdminSpecialistAggregator {

    private final AdminSpecialistQueryService queryService;
    private final SystemUserService userService;

    // WARNING: transaction can be till userService in the same app context
    @Transactional(readOnly = true)
    @Override
    public PageResponse<SpecialistAggregatedResponseDto> aggregate(AdminSpecialistFilter filter) {
        PageResponse<FullSpecialistResponseDto> page = queryService.findAll(filter);
        Set<UUID> creatorIds = page.data().stream()
                .map(FullSpecialistResponseDto::getCreatorId)
                .collect(Collectors.toSet());
        Map<UUID, PublicUserResponseDto> creators = userService.findAllByIdIn(creatorIds);
        List<SpecialistAggregatedResponseDto> aggregatedDtos = page.data().stream()
                .map(dto -> new SpecialistAggregatedResponseDto(creators.get(dto.getCreatorId()), dto))
                .toList();
        return new PageResponse<>(aggregatedDtos, page.totalPages());
    }
}
