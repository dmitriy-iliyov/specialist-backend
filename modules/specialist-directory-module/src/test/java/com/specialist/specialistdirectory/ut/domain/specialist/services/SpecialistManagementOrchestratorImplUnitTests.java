package com.specialist.specialistdirectory.ut.domain.specialist.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateRequest;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistManagementOrchestratorImpl;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistManagementStrategy;
import com.specialist.specialistdirectory.exceptions.NullSpecialistStrategyManagementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialistManagementOrchestratorImplUnitTests {

    @Mock
    private SpecialistManagementStrategy strategy;

    private SpecialistManagementOrchestratorImpl orchestrator;

    @BeforeEach
    void setUp() {
        when(strategy.getType()).thenReturn(ProfileType.SPECIALIST);
        orchestrator = new SpecialistManagementOrchestratorImpl(List.of(strategy));
    }

    @Test
    @DisplayName("UT: save() should delegate to strategy")
    void save_shouldDelegate() {
        SpecialistCreateDto dto = new SpecialistCreateDto("first", null, "last", 1L, null, 5, Collections.emptyList(), null, "city", "12345", Collections.emptyList(), null);
        SpecialistCreateRequest request = new SpecialistCreateRequest(UUID.randomUUID(), ProfileType.SPECIALIST, dto, null, null);
        SpecialistResponseDto responseDto = mock(SpecialistResponseDto.class);

        when(strategy.save(request)).thenReturn(responseDto);

        SpecialistResponseDto result = orchestrator.save(request);

        assertEquals(responseDto, result);
        verify(strategy).save(request);
    }

    @Test
    @DisplayName("UT: update() should delegate to strategy")
    void update_shouldDelegate() {
        SpecialistUpdateDto dto = new SpecialistUpdateDto("first", null, "last", 1L, null, 5, Collections.emptyList(), null, "city", "12345", Collections.emptyList(), null);
        SpecialistResponseDto responseDto = mock(SpecialistResponseDto.class);

        when(strategy.update(dto)).thenReturn(responseDto);

        SpecialistResponseDto result = orchestrator.update(dto, ProfileType.SPECIALIST);

        assertEquals(responseDto, result);
        verify(strategy).update(dto);
    }

    @Test
    @DisplayName("UT: delete() should delegate to strategy")
    void delete_shouldDelegate() {
        UUID accountId = UUID.randomUUID();
        UUID id = UUID.randomUUID();

        orchestrator.delete(accountId, id, ProfileType.SPECIALIST);

        verify(strategy).delete(accountId, id);
    }

    @Test
    @DisplayName("UT: resolveStrategy() when strategy not found should throw exception")
    void resolveStrategy_notFound_shouldThrow() {
        SpecialistCreateDto dto = new SpecialistCreateDto("first", null, "last", 1L, null, 5, Collections.emptyList(), null, "city", "12345", Collections.emptyList(), null);
        SpecialistCreateRequest request = new SpecialistCreateRequest(UUID.randomUUID(), ProfileType.USER, dto, null, null);
        assertThrows(NullSpecialistStrategyManagementException.class, () -> orchestrator.save(request));
    }
}
