package com.specialist.specialistdirectory.ut.domain.specialist.services.service;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import com.specialist.specialistdirectory.domain.specialist.services.service.ServiceSpecialistServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceSpecialistServiceImplUnitTests {

    @Mock
    private SpecialistService service;

    @InjectMocks
    private ServiceSpecialistServiceImpl serviceSpecialistService;

    @Test
    @DisplayName("UT: save() should set service fields and call service")
    void save_shouldSetServiceFields() {
        UUID creatorId = UUID.randomUUID();
        SpecialistCreateDto dto = new SpecialistCreateDto(
                "first", "second", "last", 1L, "another", 5, Collections.emptyList(), "details",
                "city", "12345", Collections.emptyList(), "site"
        );
        SpecialistResponseDto responseDto = mock(SpecialistResponseDto.class);

        when(service.save(dto)).thenReturn(responseDto);

        SpecialistResponseDto result = serviceSpecialistService.save(creatorId, dto);

        assertEquals(responseDto, result);
        assertEquals(creatorId, dto.getCreatorId());
        assertEquals(CreatorType.SERVICE, dto.getCreatorType());
        assertEquals(SpecialistStatus.APPROVED, dto.getStatus());
        assertEquals(SpecialistState.DEFAULT, dto.getState());
        verify(service).save(dto);
    }
}
