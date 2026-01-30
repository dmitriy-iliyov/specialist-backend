package com.specialist.specialistdirectory.ut.domain.specialist.services.admin;

import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.specialist.specialistdirectory.domain.specialist.models.enums.ApproverType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.CreatorType;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistState;
import com.specialist.specialistdirectory.domain.specialist.models.enums.SpecialistStatus;
import com.specialist.specialistdirectory.domain.specialist.models.filters.AdminSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistStatusService;
import com.specialist.specialistdirectory.domain.specialist.services.admin.AdminSpecialistAggregator;
import com.specialist.specialistdirectory.domain.specialist.services.admin.AdminSpecialistQueryService;
import com.specialist.specialistdirectory.domain.specialist.services.admin.AdminSpecialistServiceImpl;
import com.specialist.utils.pagination.PageResponse;
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
class AdminSpecialistServiceImplUnitTests {

    @Mock
    private SpecialistService service;
    @Mock
    private SpecialistStatusService statusService;
    @Mock
    private AdminSpecialistAggregator aggregator;
    @Mock
    private AdminSpecialistQueryService queryService;

    @InjectMocks
    private AdminSpecialistServiceImpl adminService;

    @Test
    @DisplayName("UT: save() should set admin fields and call service")
    void save_shouldSetAdminFields() {
        UUID creatorId = UUID.randomUUID();
        SpecialistCreateDto dto = new SpecialistCreateDto("first", null, "last", 1L, null, 5, Collections.emptyList(), null, "city", "12345", Collections.emptyList(), null);
        SpecialistResponseDto responseDto = mock(SpecialistResponseDto.class);

        when(service.save(dto)).thenReturn(responseDto);

        SpecialistResponseDto result = adminService.save(creatorId, dto);

        assertEquals(responseDto, result);
        assertEquals(creatorId, dto.getCreatorId());
        assertEquals(CreatorType.ADMIN, dto.getCreatorType());
        assertEquals(SpecialistStatus.APPROVED, dto.getStatus());
        assertEquals(SpecialistState.DEFAULT, dto.getState());
        verify(service).save(dto);
    }

    @Test
    @DisplayName("UT: findAll() when aggregate is true should call aggregator")
    void findAll_aggregateTrue_shouldCallAggregator() {
        AdminSpecialistFilter filter = new AdminSpecialistFilter(null, null, null, null, null, null, null, null, null, null, null, true, true, 0, 10);
        PageResponse response = mock(PageResponse.class);

        when(aggregator.aggregate(filter)).thenReturn(response);

        PageResponse<?> result = adminService.findAll(filter);

        assertEquals(response, result);
        verify(aggregator).aggregate(filter);
        verifyNoInteractions(queryService);
    }

    @Test
    @DisplayName("UT: findAll() when aggregate is false should call queryService")
    void findAll_aggregateFalse_shouldCallQueryService() {
        AdminSpecialistFilter filter = new AdminSpecialistFilter(null, null, null, null, null, null, null, null, null, null, null, false, true, 0, 10);
        PageResponse response = mock(PageResponse.class);

        when(queryService.findAll(filter)).thenReturn(response);

        PageResponse<?> result = adminService.findAll(filter);

        assertEquals(response, result);
        verify(queryService).findAll(filter);
        verifyNoInteractions(aggregator);
    }

    @Test
    @DisplayName("UT: findAll() when aggregate is null should call queryService")
    void findAll_aggregateNull_shouldCallQueryService() {
        AdminSpecialistFilter filter = new AdminSpecialistFilter(null, null, null, null, null, null, null, null, null, null, null, null, true, 0, 10);
        PageResponse response = mock(PageResponse.class);

        when(queryService.findAll(filter)).thenReturn(response);

        PageResponse<?> result = adminService.findAll(filter);

        assertEquals(response, result);
        verify(queryService).findAll(filter);
        verifyNoInteractions(aggregator);
    }

    @Test
    @DisplayName("UT: approve() should call statusService")
    void approve_shouldCallStatusService() {
        UUID id = UUID.randomUUID();
        UUID approverId = UUID.randomUUID();

        adminService.approve(id, approverId);

        verify(statusService).approve(id, approverId, ApproverType.ADMIN);
    }

    @Test
    @DisplayName("UT: findById() should delegate")
    void findById_shouldDelegate() {
        UUID id = UUID.randomUUID();
        adminService.findById(id);
        verify(service).findById(id);
    }

    @Test
    @DisplayName("UT: update() should delegate")
    void update_shouldDelegate() {
        SpecialistUpdateDto dto = mock(SpecialistUpdateDto.class);
        adminService.update(dto);
        verify(service).update(dto);
    }

    @Test
    @DisplayName("UT: deleteById() should delegate")
    void deleteById_shouldDelegate() {
        UUID id = UUID.randomUUID();
        adminService.deleteById(id);
        verify(service).deleteById(id);
    }
}
