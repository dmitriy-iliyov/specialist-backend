package com.specialist.specialistdirectory.domain.type.controllers;

import com.specialist.specialistdirectory.domain.type.models.dtos.ShortTypeResponseDto;
import com.specialist.specialistdirectory.domain.type.services.TypeReadService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TypeControllerUnitTests {

    @Mock
    private TypeReadService orchestrator;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private TypeController controller;

    @Test
    @DisplayName("UT: getAllAsJson() should call orchestrator and return OK")
    void getAllAsJson_shouldCallOrchestrator() {
        List<ShortTypeResponseDto> list = List.of(new ShortTypeResponseDto(1L, "title"));
        when(orchestrator.findAllApprovedAsJson(request)).thenReturn(list);

        ResponseEntity<?> result = controller.getAllAsJson(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(list, result.getBody());
        verify(orchestrator).findAllApprovedAsJson(request);
    }

    @Test
    @DisplayName("UT: getAllAsMap() should call orchestrator and return OK")
    void getAllAsMap_shouldCallOrchestrator() {
        Map<Long, String> map = Map.of(1L, "title");
        when(orchestrator.findAllApprovedAsMap(request)).thenReturn(map);

        ResponseEntity<?> result = controller.getAllAsMap(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(map, result.getBody());
        verify(orchestrator).findAllApprovedAsMap(request);
    }
}
