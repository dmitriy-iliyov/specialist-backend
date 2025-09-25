package com.specialist.profile.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.contracts.specialistdirectory.SystemManagedSpecialistService;
import com.specialist.contracts.specialistdirectory.dto.ManagedSpecialistResponseDto;
import com.specialist.profile.mappers.SpecialistProfileMapper;
import com.specialist.profile.models.ProfileFilter;
import com.specialist.profile.models.dtos.PrivateSpecialistResponseDto;
import com.specialist.profile.models.dtos.PublicSpecialistResponseDto;
import com.specialist.profile.models.enums.ScopeType;
import com.specialist.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecialistProfileAggregateStrategy implements ProfileReadStrategy {

    private final SpecialistProfileService service;
    private final SystemManagedSpecialistService systemManagedSpecialistService;
    private final SpecialistProfileMapper mapper;

    @Override
    public ProfileType getType() {
        return ProfileType.SPECIALIST;
    }

    @Transactional(readOnly = true)
    @Override
    public PrivateSpecialistResponseDto findPrivateById(UUID id) {
        PrivateSpecialistResponseDto dto = service.findPrivateById(id);
        if (dto.hasCard()) {
            ManagedSpecialistResponseDto managedDto = systemManagedSpecialistService.findById(id);
            return mapper.aggregate(dto, managedDto);
        }
        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public PublicSpecialistResponseDto findPublicById(UUID id) {
        PublicSpecialistResponseDto dto = service.findPublicById(id);
        if (dto.hasCard()) {
            ManagedSpecialistResponseDto managedDto = systemManagedSpecialistService.findById(id);
            return mapper.aggregate(dto, managedDto);
        }
        return dto;    }

    // FIXME if scope PUBLIC
    @Transactional(readOnly = true)
    @Override
    public PageResponse<?> findAll(ScopeType scope, ProfileFilter filter) {
        if (scope.equals(ScopeType.PRIVATE)) {
            PageResponse<PrivateSpecialistResponseDto> dtoPage = service.findAll(filter);
            Set<UUID> ids = dtoPage.data().stream()
                    .filter(PrivateSpecialistResponseDto::hasCard)
                    .map(PrivateSpecialistResponseDto::getId)
                    .collect(Collectors.toSet());
            if (!ids.isEmpty()) {
                List<ManagedSpecialistResponseDto> managedDtoPage = systemManagedSpecialistService.findAll(filter);
                return new PageResponse<>(
                        mapper.aggregate(dtoPage.data(), managedDtoPage),
                        dtoPage.totalPages()
                );
            }
            return dtoPage;
        } else {
            return new PageResponse<>(Collections.emptyList(), 0L);
        }
    }
}