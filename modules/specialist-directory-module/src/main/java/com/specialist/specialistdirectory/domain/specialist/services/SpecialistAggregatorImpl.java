package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.contracts.user.PublicUserResponseDto;
import com.specialist.contracts.user.SystemUserService;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistAggregatedResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecialistAggregatorImpl implements SpecialistAggregator {

    // WARNING: @Transactional can be till systemUserService in the same app context
    private final SpecialistService specialistService;
    private final SystemUserService userService;

    @Cacheable(value = "specialists:all", key = "#page.cacheKey()", condition = "#page.pageNumber() < 3")
    @Transactional(readOnly = true)
    @Override
    public PageResponse<SpecialistAggregatedResponseDto> findAll(PageRequest page) {
        return preparePageResponse(specialistService.findAll(page));
    }

    @Cacheable(value = "specialists:filter", key = "#filter.cacheKey()", condition = "#filter.pageNumber() < 2")
    @Transactional(readOnly = true)
    @Override
    public PageResponse<SpecialistAggregatedResponseDto> findAllByFilter(SpecialistFilter filter) {
        return preparePageResponse(specialistService.findAllByFilter(filter));
    }

    private PageResponse<SpecialistAggregatedResponseDto> preparePageResponse(PageResponse<SpecialistResponseDto> pageResponse) {
        Set<UUID> creatorIds = pageResponse.data().stream().map(SpecialistResponseDto::getCreatorId).collect(Collectors.toSet());
        Map<UUID, PublicUserResponseDto> creatorsMap = userService.findAllByIdIn(creatorIds);
        return new PageResponse<>(
                pageResponse.data().stream()
                        .map(dto -> new SpecialistAggregatedResponseDto(creatorsMap.get(dto.getCreatorId()), dto))
                        .toList(),
                pageResponse.totalPages()
        );
    }
}
