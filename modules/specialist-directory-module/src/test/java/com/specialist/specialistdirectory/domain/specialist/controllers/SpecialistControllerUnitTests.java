package com.specialist.specialistdirectory.domain.specialist.controllers;

import com.specialist.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistAggregator;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistCountService;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialistControllerUnitTests {

    @Mock
    private SpecialistAggregator aggregator;

    @Mock
    private SpecialistCountService countService;

    @InjectMocks
    private SpecialistController controller;

    @Test
    @DisplayName("UT: getAll() with empty filter should call aggregate with PageRequest")
    void getAll_emptyFilter_shouldCallAggregateWithPageRequest() {
        SpecialistFilter filter = new SpecialistFilter(null, null, null, null, null, null, true, 0, 10);
        PageResponse response = new PageResponse(Collections.emptyList(), 0);

        when(aggregator.aggregate(any(PageRequest.class))).thenReturn(response);

        ResponseEntity<?> result = controller.getAll(filter);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(aggregator).aggregate(any(PageRequest.class));
        verify(aggregator, never()).aggregate(any(SpecialistFilter.class));
    }

    @Test
    @DisplayName("UT: getAll() with non-empty filter should call aggregate with filter")
    void getAll_nonEmptyFilter_shouldCallAggregateWithFilter() {
        SpecialistFilter filter = new SpecialistFilter("city", null, null, null, null, null, true, 0, 10);
        PageResponse response = new PageResponse(Collections.emptyList(), 0);

        when(aggregator.aggregate(filter)).thenReturn(response);

        ResponseEntity<?> result = controller.getAll(filter);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(aggregator).aggregate(filter);
        verify(aggregator, never()).aggregate(any(PageRequest.class));
    }
}
