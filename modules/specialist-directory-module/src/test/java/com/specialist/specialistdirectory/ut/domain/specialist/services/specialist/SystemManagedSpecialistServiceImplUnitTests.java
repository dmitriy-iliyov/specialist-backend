package com.specialist.specialistdirectory.ut.domain.specialist.services.specialist;

import com.specialist.contracts.specialistdirectory.dto.ExternalManagedSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.mappers.ManagedSpecialistMapper;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import com.specialist.specialistdirectory.domain.specialist.models.filters.SystemSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.services.SystemSpecialistService;
import com.specialist.specialistdirectory.domain.specialist.services.specialist.SystemManagedSpecialistServiceImpl;
import com.specialist.utils.pagination.PageDataHolder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SystemManagedSpecialistServiceImplUnitTests {

    @Mock
    private SystemSpecialistService service;

    @Mock
    private ManagedSpecialistMapper mapper;

    @InjectMocks
    private SystemManagedSpecialistServiceImpl managedService;

    @Test
    @DisplayName("UT: findById() should find by id and state MANAGED")
    void findById_shouldFindByIdAndState() {
        UUID id = UUID.randomUUID();
        SpecialistResponseDto dto = mock(SpecialistResponseDto.class);
        ExternalManagedSpecialistResponseDto externalDto = mock(ExternalManagedSpecialistResponseDto.class);

        when(service.findByIdAndState(id, SpecialistState.MANAGED)).thenReturn(dto);
        when(mapper.toExternalManagedDto(dto)).thenReturn(externalDto);

        ExternalManagedSpecialistResponseDto result = managedService.findById(id);

        assertEquals(externalDto, result);
        verify(service).findByIdAndState(id, SpecialistState.MANAGED);
    }

    @Test
    @DisplayName("UT: findAll() should find all with state MANAGED")
    void findAll_shouldFindAllWithState() {
        PageDataHolder page = mock(PageDataHolder.class);
        List<SpecialistResponseDto> dtoList = List.of(mock(SpecialistResponseDto.class));
        List<ExternalManagedSpecialistResponseDto> externalList = List.of(mock(ExternalManagedSpecialistResponseDto.class));

        when(service.findAll(any(SystemSpecialistFilter.class))).thenReturn(dtoList);
        when(mapper.toExternalManagedDtoList(dtoList)).thenReturn(externalList);

        List<ExternalManagedSpecialistResponseDto> result = managedService.findAll(page);

        assertEquals(externalList, result);
        verify(service).findAll(any(SystemSpecialistFilter.class));
    }
}
