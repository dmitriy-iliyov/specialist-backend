package com.specialist.specialistdirectory.ut.domain.specialist.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.contracts.profile.SystemProfileService;
import com.specialist.contracts.profile.dto.UnifiedProfileResponseDto;
import com.specialist.contracts.schedule.NearestIntervalDto;
import com.specialist.contracts.schedule.SystemNearestIntervalService;
import com.specialist.specialistdirectory.domain.specialist.mappers.ManagedSpecialistMapper;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.ManagedSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistAggregatedResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistAggregatorImpl;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import com.specialist.utils.pagination.PageRequest;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialistAggregatorImplUnitTests {

    @Mock
    private SpecialistService specialistService;
    @Mock
    private SystemProfileService profileService;
    @Mock
    private SystemNearestIntervalService nearestIntervalService;
    @Mock
    private ManagedSpecialistMapper managedSpecialistMapper;

    @InjectMocks
    private SpecialistAggregatorImpl aggregator;

    @Test
    @DisplayName("UT: aggregate() should correctly map specialist and user profiles")
    void aggregate_shouldMapProfilesCorrectly() {
        PageRequest pageRequest = new PageRequest(0, 10, true);
        
        UUID specialistOwnerId = UUID.randomUUID();
        UUID userOwnerId = UUID.randomUUID();
        
        SpecialistResponseDto specialistOwnedBySpecialist = mock(SpecialistResponseDto.class);
        when(specialistOwnedBySpecialist.getOwnerId()).thenReturn(specialistOwnerId);
        
        SpecialistResponseDto specialistOwnedByUser = mock(SpecialistResponseDto.class);
        when(specialistOwnedByUser.getOwnerId()).thenReturn(userOwnerId);

        PageResponse<SpecialistResponseDto> specialistPage = new PageResponse<>(List.of(specialistOwnedBySpecialist, specialistOwnedByUser), 1);
        
        UnifiedProfileResponseDto specialistProfile = new UnifiedProfileResponseDto(specialistOwnerId, ProfileType.SPECIALIST, "Specialist Name", 5.0);
        specialistProfile.setAvatarUrl("avatar.url");
        
        UnifiedProfileResponseDto userProfile = new UnifiedProfileResponseDto(userOwnerId, ProfileType.USER, "User Name", 4.0);
        userProfile.setAvatarUrl("avatar.url");
        
        NearestIntervalDto nearestInterval = mock(NearestIntervalDto.class);
        ManagedSpecialistResponseDto managedDto = mock(ManagedSpecialistResponseDto.class);

        when(specialistService.findAll(pageRequest)).thenReturn(specialistPage);
        when(profileService.findAllByIdIn(Set.of(specialistOwnerId, userOwnerId))).thenReturn(Map.of(specialistOwnerId, specialistProfile, userOwnerId, userProfile));
        when(nearestIntervalService.findAllByIdIn(Set.of(specialistOwnerId))).thenReturn(Map.of(specialistOwnerId, nearestInterval));
        when(managedSpecialistMapper.toManagedDto(specialistOwnedBySpecialist, "avatar.url")).thenReturn(managedDto);

        PageResponse<SpecialistAggregatedResponseDto> result = aggregator.aggregate(pageRequest);

        assertEquals(2, result.data().size());

        SpecialistAggregatedResponseDto aggregatedForSpecialist = result.data().stream().filter(d -> d.creator() == null).findFirst().orElse(null);
        assertNotNull(aggregatedForSpecialist);
        assertEquals(managedDto, aggregatedForSpecialist.specialist());
        assertEquals(nearestInterval, aggregatedForSpecialist.nearestInterval());

        SpecialistAggregatedResponseDto aggregatedForUser = result.data().stream().filter(d -> d.creator() != null).findFirst().orElse(null);
        assertNotNull(aggregatedForUser);
        assertEquals(userProfile, aggregatedForUser.creator());
        assertEquals(specialistOwnedByUser, aggregatedForUser.specialist());
        assertNull(aggregatedForUser.nearestInterval());
        
        verify(specialistService).findAll(pageRequest);
        verify(profileService).findAllByIdIn(anySet());
        verify(nearestIntervalService).findAllByIdIn(anySet());
        verify(managedSpecialistMapper).toManagedDto(any(), any());
    }
}
