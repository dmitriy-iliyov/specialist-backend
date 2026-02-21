package com.specialist.specialistdirectory.domain.specialist.services.specialist;

import com.specialist.contracts.auth.DemoteRequest;
import com.specialist.contracts.auth.SystemAccountDemoteFacade;
import com.specialist.contracts.profile.ProfileType;
import com.specialist.contracts.profile.SystemSpecialistProfileService;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.*;
import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import com.specialist.specialistdirectory.exceptions.OwnershipException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManagedSpecialistManagementStrategyUnitTests {

    @Mock
    private SpecialistService service;
    @Mock
    private SystemSpecialistProfileService specialistProfileService;
    @Mock
    private SystemAccountDemoteFacade accountDemoteService;

    @InjectMocks
    private ManagedSpecialistManagementStrategy strategy;

    @Test
    @DisplayName("UT: getType() should return SPECIALIST")
    void getType_shouldReturnSpecialist() {
        assertEquals(ProfileType.SPECIALIST, strategy.getType());
    }

    @Test
    @DisplayName("UT: save() should set fields, save, set card id and demote")
    void save_shouldSetFieldsAndSave() {
        UUID creatorId = UUID.randomUUID();
        SpecialistCreateDto dto = new SpecialistCreateDto("first", null, "last", 1L, null, 5, Collections.emptyList(), null, "city", "12345", Collections.emptyList(), null);
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);
        SpecialistCreateRequest request = new SpecialistCreateRequest(creatorId, ProfileType.SPECIALIST, dto, httpRequest, httpResponse);
        
        SpecialistResponseDto responseDto = mock(SpecialistResponseDto.class);
        when(responseDto.getId()).thenReturn(UUID.randomUUID());

        when(service.save(dto)).thenReturn(responseDto);

        SpecialistResponseDto result = strategy.save(request);

        assertEquals(responseDto, result);
        assertEquals(creatorId, dto.getCreatorId());
        assertEquals(CreatorType.SPECIALIST, dto.getCreatorType());
        assertEquals(SpecialistStatus.UNAPPROVED, dto.getStatus());
        assertEquals(SpecialistState.MANAGED, dto.getState());
        
        verify(service).save(dto);
        verify(specialistProfileService).setSpecialistCardId(responseDto.getId());
        verify(accountDemoteService).demote(any(DemoteRequest.class));
    }

    @Test
    @DisplayName("UT: update() when owner matches should update")
    void update_ownerMatches_shouldUpdate() {
        UUID accountId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        SpecialistUpdateDto dto = new SpecialistUpdateDto("first", null, "last", 1L, null, 5, Collections.emptyList(), null, "city", "12345", Collections.emptyList(), null);
        dto.setAccountId(accountId);
        dto.setId(id);
        
        ShortSpecialistInfo info = new ShortSpecialistInfo(id, UUID.randomUUID(), accountId, SpecialistStatus.APPROVED);
        SpecialistResponseDto responseDto = mock(SpecialistResponseDto.class);

        when(service.getShortInfoById(id)).thenReturn(info);
        when(service.update(dto)).thenReturn(responseDto);

        SpecialistResponseDto result = strategy.update(dto);

        assertEquals(responseDto, result);
        verify(service).update(dto);
    }

    @Test
    @DisplayName("UT: update() when owner mismatch should throw exception")
    void update_ownerMismatch_shouldThrow() {
        UUID accountId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        SpecialistUpdateDto dto = new SpecialistUpdateDto("first", null, "last", 1L, null, 5, Collections.emptyList(), null, "city", "12345", Collections.emptyList(), null);
        dto.setAccountId(accountId);
        dto.setId(id);
        
        ShortSpecialistInfo info = new ShortSpecialistInfo(id, UUID.randomUUID(), UUID.randomUUID(), SpecialistStatus.APPROVED); // Different owner

        when(service.getShortInfoById(id)).thenReturn(info);

        assertThrows(OwnershipException.class, () -> strategy.update(dto));
    }

    @Test
    @DisplayName("UT: delete() when owner matches should delete")
    void delete_ownerMatches_shouldDelete() {
        UUID accountId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        ShortSpecialistInfo info = new ShortSpecialistInfo(id, UUID.randomUUID(), accountId, SpecialistStatus.APPROVED);

        when(service.getShortInfoById(id)).thenReturn(info);

        strategy.delete(accountId, id);

        verify(service).deleteById(id);
    }

    @Test
    @DisplayName("UT: delete() when owner mismatch should throw exception")
    void delete_ownerMismatch_shouldThrow() {
        UUID accountId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        ShortSpecialistInfo info = new ShortSpecialistInfo(id, UUID.randomUUID(), UUID.randomUUID(), SpecialistStatus.APPROVED); // Different owner

        when(service.getShortInfoById(id)).thenReturn(info);

        assertThrows(OwnershipException.class, () -> strategy.delete(accountId, id));
    }
}
