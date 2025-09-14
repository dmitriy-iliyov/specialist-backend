package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.contracts.user.SystemProfileReadService;
import com.specialist.contracts.user.dto.UnifiedProfileResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistAggregatedResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SpecialistAggregatorImpl implements SpecialistAggregator {

    // WARNING: @Transactional can be till profileQueryService in the same app context
    private final SpecialistService specialistService;
    private final SystemProfileReadService profileQueryService;

    public SpecialistAggregatorImpl(SpecialistService specialistService,
                                    @Qualifier("defaultSystemProfileReadService") SystemProfileReadService profileQueryService) {
        this.specialistService = specialistService;
        this.profileQueryService = profileQueryService;
    }

    @Cacheable(value = "specialists:all", key = "#page.cacheKey()", condition = "#page.pageNumber() < 3")
    @Transactional(readOnly = true)
    @Override
    public PageResponse<SpecialistAggregatedResponseDto> aggregate(PageRequest page) {
        return preparePageResponse(specialistService.findAll(page));
    }

    @Cacheable(value = "specialists:filter", key = "#filter.cacheKey()", condition = "#filter.pageNumber() < 2")
    @Transactional(readOnly = true)
    @Override
    public PageResponse<SpecialistAggregatedResponseDto> aggregate(SpecialistFilter filter) {
        return preparePageResponse(specialistService.findAllByFilter(filter));
    }

    private PageResponse<SpecialistAggregatedResponseDto> preparePageResponse(PageResponse<SpecialistResponseDto> pageResponse) {
        Set<UUID> ownersIds = pageResponse.data().stream().map(SpecialistResponseDto::getOwnerId).collect(Collectors.toSet());
        Map<UUID, UnifiedProfileResponseDto> ownersMap = profileQueryService.findAllByIdIn(ownersIds);
        return new PageResponse<>(
                pageResponse.data().stream()
                        .map(dto -> new SpecialistAggregatedResponseDto(ownersMap.get(dto.getOwnerId()), dto))
                        .toList(),
                pageResponse.totalPages()
        );
    }
}
