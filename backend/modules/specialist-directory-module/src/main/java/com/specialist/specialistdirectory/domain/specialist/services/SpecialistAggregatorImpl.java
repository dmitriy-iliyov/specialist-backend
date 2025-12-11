package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.contracts.profile.SystemProfileService;
import com.specialist.contracts.profile.dto.UnifiedProfileResponseDto;
import com.specialist.contracts.schedule.NearestIntervalDto;
import com.specialist.contracts.schedule.SystemNearestIntervalService;
import com.specialist.specialistdirectory.domain.specialist.mappers.ManagedSpecialistMapper;
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

    private final SpecialistService specialistService;
    private final SystemProfileService profileService;
    private final SystemNearestIntervalService nearestIntervalService;
    private final ManagedSpecialistMapper managedSpecialistMapper;

    public SpecialistAggregatorImpl(SpecialistService specialistService,
                                    @Qualifier("defaultSystemProfileService") SystemProfileService profileService,
                                    SystemNearestIntervalService nearestIntervalService, ManagedSpecialistMapper managedSpecialistMapper) {
        this.specialistService = specialistService;
        this.profileService = profileService;
        this.nearestIntervalService = nearestIntervalService;
        this.managedSpecialistMapper = managedSpecialistMapper;
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
        Map<UUID, UnifiedProfileResponseDto> ownersMap = profileService.findAllByIdIn(ownersIds);
        Set<UUID> specialistProfileIds = ownersMap.values().stream()
                .filter(dto -> dto.getType().equals(ProfileType.SPECIALIST))
                .map(UnifiedProfileResponseDto::getId)
                .collect(Collectors.toSet());
        Map<UUID, NearestIntervalDto> nearestIntervalDtoMap = nearestIntervalService.findAllByIdIn(specialistProfileIds);
        return new PageResponse<>(
                pageResponse.data().stream()
                        .map(dto -> {
                            UnifiedProfileResponseDto owner = ownersMap.get(dto.getOwnerId());
                            if (owner.getType().equals(ProfileType.SPECIALIST)) {
                                return new SpecialistAggregatedResponseDto(
                                        null,
                                        managedSpecialistMapper.toManagedDto(dto, owner.getAvatarUrl()),
                                        nearestIntervalDtoMap.get(dto.getOwnerId())
                                );
                            }
                            return new SpecialistAggregatedResponseDto(
                                    owner,
                                    dto,
                                    null
                            );
                        })
                        .toList(),
                pageResponse.totalPages()
        );
    }
}
