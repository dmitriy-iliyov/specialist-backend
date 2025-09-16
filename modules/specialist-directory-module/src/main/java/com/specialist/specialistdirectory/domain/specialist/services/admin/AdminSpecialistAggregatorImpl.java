package com.specialist.specialistdirectory.domain.specialist.services.admin;

import com.specialist.contracts.profile.SystemProfileAggregator;
import com.specialist.contracts.profile.dto.UnifiedProfileResponseDto;
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
    private final SystemProfileAggregator profileAggregator;

    public AdminSpecialistAggregatorImpl(AdminSpecialistQueryService specialistQueryService,
                                         @Qualifier("defaultSystemProfileAggregator") SystemProfileAggregator profileAggregator) {
        this.specialistQueryService = specialistQueryService;
        this.profileAggregator = profileAggregator;
    }

    // WARNING: transaction can be till profileReadService in the same app context
    @Transactional(readOnly = true)
    @Override
    public PageResponse<AdminSpecialistAggregatedResponseDto> aggregate(AdminSpecialistFilter filter) {
        PageResponse<FullSpecialistResponseDto> page = specialistQueryService.findAll(filter);
        Set<UUID> ownersIds = page.data().stream()
                .map(FullSpecialistResponseDto::getOwnerId)
                .collect(Collectors.toSet());
        Map<UUID, UnifiedProfileResponseDto> ownersMap = profileAggregator.findAllByIdIn(ownersIds);
        List<AdminSpecialistAggregatedResponseDto> aggregatedDtos = page.data().stream()
                .map(dto -> new AdminSpecialistAggregatedResponseDto(ownersMap.get(dto.getOwnerId()), dto))
                .toList();
        return new PageResponse<>(aggregatedDtos, page.totalPages());
    }
}