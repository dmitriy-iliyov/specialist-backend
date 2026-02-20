package com.specialist.specialistdirectory.ut.domain.specialist.services.creator;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkCreateDto;
import com.specialist.specialistdirectory.domain.bookmark.services.BookmarkPersistService;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.*;
import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistCacheService;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import com.specialist.specialistdirectory.domain.specialist.services.creator.CreatorSpecialistManagementStrategy;
import com.specialist.specialistdirectory.exceptions.OwnershipException;
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
class CreatorSpecialistManagementStrategyUnitTests {

    @Mock
    private SpecialistService specialistService;
    @Mock
    private SpecialistCacheService cacheService;
    @Mock
    private BookmarkPersistService bookmarkPersistService;

    @InjectMocks
    private CreatorSpecialistManagementStrategy strategy;

    @Test
    @DisplayName("UT: getType() should return USER")
    void getType_shouldReturnUser() {
        assertEquals(ProfileType.USER, strategy.getType());
    }

    @Test
    @DisplayName("UT: save() should set fields, save, evict cache and save bookmark")
    void save_shouldSetFieldsAndSave() {
        UUID creatorId = UUID.randomUUID();
        SpecialistCreateDto dto = new SpecialistCreateDto("first", null, "last", 1L, null, 5, Collections.emptyList(), null, "city", "12345", Collections.emptyList(), null);
        SpecialistCreateRequest request = new SpecialistCreateRequest(creatorId, ProfileType.USER, dto, null, null);
        
        SpecialistResponseDto responseDto = mock(SpecialistResponseDto.class);
        when(responseDto.getOwnerId()).thenReturn(creatorId);
        when(responseDto.getId()).thenReturn(UUID.randomUUID());

        when(specialistService.save(dto)).thenReturn(responseDto);

        SpecialistResponseDto result = strategy.save(request);

        assertEquals(responseDto, result);
        assertEquals(creatorId, dto.getCreatorId());
        assertEquals(CreatorType.USER, dto.getCreatorType());
        assertEquals(SpecialistStatus.UNAPPROVED, dto.getStatus());
        assertEquals(SpecialistState.DEFAULT, dto.getState());
        
        verify(specialistService).save(dto);
        verify(cacheService).evictCacheAfterSave(creatorId);
        verify(bookmarkPersistService).saveAfterSpecialistCreate(any(BookmarkCreateDto.class));
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

        when(specialistService.getShortInfoById(id)).thenReturn(info);
        when(specialistService.update(dto)).thenReturn(responseDto);

        SpecialistResponseDto result = strategy.update(dto);

        assertEquals(responseDto, result);
        verify(specialistService).update(dto);
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

        when(specialistService.getShortInfoById(id)).thenReturn(info);

        assertThrows(OwnershipException.class, () -> strategy.update(dto));
    }

    @Test
    @DisplayName("UT: delete() when owner matches should delete")
    void delete_ownerMatches_shouldDelete() {
        UUID accountId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        ShortSpecialistInfo info = new ShortSpecialistInfo(id, UUID.randomUUID(), accountId, SpecialistStatus.APPROVED);

        when(specialistService.getShortInfoById(id)).thenReturn(info);

        strategy.delete(accountId, id);

        verify(specialistService).deleteById(id);
    }

    @Test
    @DisplayName("UT: delete() when owner mismatch should throw exception")
    void delete_ownerMismatch_shouldThrow() {
        UUID accountId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        ShortSpecialistInfo info = new ShortSpecialistInfo(id, UUID.randomUUID(), UUID.randomUUID(), SpecialistStatus.APPROVED); // Different owner

        when(specialistService.getShortInfoById(id)).thenReturn(info);

        assertThrows(OwnershipException.class, () -> strategy.delete(accountId, id));
    }
}
