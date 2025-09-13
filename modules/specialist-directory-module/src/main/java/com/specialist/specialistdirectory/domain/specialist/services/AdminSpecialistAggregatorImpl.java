package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.contracts.user.SystemUserReadService;
import com.specialist.contracts.user.dto.PublicUserResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.AdminSpecialistAggregatedResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.FullSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.filters.AdminSpecialistFilter;
import com.specialist.utils.pagination.PageResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminSpecialistAggregatorImpl implements AdminSpecialistAggregator {

    private final AdminSpecialistQueryService specialistQueryService;
    private final SystemUserReadService userQueryService;

    public AdminSpecialistAggregatorImpl(AdminSpecialistQueryService specialistQueryService,
                                         @Qualifier("compositeSystemUserReadService")
                                         SystemUserReadService userQueryService) {
        this.specialistQueryService = specialistQueryService;
        this.userQueryService = userQueryService;
    }

    // WARNING: transaction can be till userService in the same app context
    @Transactional(readOnly = true)
    @Override
    public PageResponse<AdminSpecialistAggregatedResponseDto> aggregate(AdminSpecialistFilter filter) {
        PageResponse<FullSpecialistResponseDto> page = specialistQueryService.findAll(filter);
        Set<UUID> creatorIds = page.data().stream()
                .map(FullSpecialistResponseDto::getCreatorId)
                .collect(Collectors.toSet());
        Map<UUID, PublicUserResponseDto> creators = userQueryService.findAllByIdIn(creatorIds);
        List<AdminSpecialistAggregatedResponseDto> aggregatedDtos = page.data().stream()
                .map(dto -> new AdminSpecialistAggregatedResponseDto(creators.get(dto.getCreatorId()), dto))
                .toList();
        return new PageResponse<>(aggregatedDtos, page.totalPages());
    }
}