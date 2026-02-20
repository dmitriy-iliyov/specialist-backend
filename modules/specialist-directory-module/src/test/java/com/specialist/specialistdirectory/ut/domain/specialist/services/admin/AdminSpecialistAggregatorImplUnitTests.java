package com.specialist.specialistdirectory.ut.domain.specialist.services.admin;

import com.specialist.contracts.profile.SystemProfileService;
import com.specialist.contracts.profile.dto.UnifiedProfileResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.AdminSpecialistAggregatedResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.FullSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.filters.AdminSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.services.admin.AdminSpecialistAggregatorImpl;
import com.specialist.specialistdirectory.domain.specialist.services.admin.AdminSpecialistQueryService;
import com.specialist.utils.pagination.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminSpecialistAggregatorImplUnitTests {

    @Mock
    private AdminSpecialistQueryService specialistQueryService;

    @Mock
    private SystemProfileService profileService;

    @InjectMocks
    private AdminSpecialistAggregatorImpl aggregator;

    @Test
    @DisplayName("UT: aggregate() should aggregate specialists and profiles")
    void aggregate_shouldAggregate() {
        AdminSpecialistFilter filter = new AdminSpecialistFilter(null, null, null, null, null, null, null, null, null, null, null, true, true, 0, 10);
        UUID ownerId = UUID.randomUUID();
        FullSpecialistResponseDto specialistDto = mock(FullSpecialistResponseDto.class);
        when(specialistDto.getOwnerId()).thenReturn(ownerId);
        PageResponse<FullSpecialistResponseDto> pageResponse = new PageResponse<>(List.of(specialistDto), 1);
        UnifiedProfileResponseDto profileDto = mock(UnifiedProfileResponseDto.class);

        when(specialistQueryService.findAll(filter)).thenReturn(pageResponse);
        when(profileService.findAllByIdIn(Set.of(ownerId))).thenReturn(Map.of(ownerId, profileDto));

        PageResponse<AdminSpecialistAggregatedResponseDto> result = aggregator.aggregate(filter);

        assertEquals(1, result.data().size());
        assertEquals(profileDto, result.data().get(0).creator());
        assertEquals(specialistDto, result.data().get(0).specialist());
    }
}
