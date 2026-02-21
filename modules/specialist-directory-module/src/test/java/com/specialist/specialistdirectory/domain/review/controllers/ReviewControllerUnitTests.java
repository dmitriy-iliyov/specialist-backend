package com.specialist.specialistdirectory.domain.review.controllers;

import com.specialist.contracts.auth.PrincipalDetails;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateRequest;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewDeleteRequest;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateRequest;
import com.specialist.specialistdirectory.domain.review.models.filters.ReviewSort;
import com.specialist.specialistdirectory.domain.review.services.ReviewAggregator;
import com.specialist.specialistdirectory.domain.review.services.ReviewManagementFacade;
import com.specialist.utils.pagination.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewControllerUnitTests {

    @Mock
    private ReviewManagementFacade facade;

    @Mock
    private ReviewAggregator aggregator;

    @Mock
    private PrincipalDetails principal;

    @Mock
    private MultipartFile picture;

    @InjectMocks
    private ReviewController controller;

    @Test
    @DisplayName("UT: create() should call facade and return CREATED")
    void create_shouldCallFacade() {
        UUID accountId = UUID.randomUUID();
        UUID specialistId = UUID.randomUUID();
        String rawPayload = "payload";
        ReviewResponseDto responseDto = new ReviewResponseDto(UUID.randomUUID(), accountId, "text", 5, null, LocalDateTime.now());

        when(principal.getAccountId()).thenReturn(accountId);
        when(facade.save(any(ReviewCreateRequest.class))).thenReturn(responseDto);

        ResponseEntity<?> result = controller.create(principal, specialistId.toString(), rawPayload, picture);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        verify(facade).save(any(ReviewCreateRequest.class));
    }

    @Test
    @DisplayName("UT: update() should call facade and return OK")
    void update_shouldCallFacade() {
        UUID accountId = UUID.randomUUID();
        UUID specialistId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        String rawPayload = "payload";
        ReviewResponseDto responseDto = new ReviewResponseDto(id, accountId, "text", 5, null, LocalDateTime.now());

        when(principal.getAccountId()).thenReturn(accountId);
        when(facade.update(any(ReviewUpdateRequest.class))).thenReturn(responseDto);

        ResponseEntity<?> result = controller.update(principal, id.toString(), specialistId.toString(), rawPayload, picture);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseDto, result.getBody());
        verify(facade).update(any(ReviewUpdateRequest.class));
    }

    @Test
    @DisplayName("UT: delete() should call facade and return NO_CONTENT")
    void delete_shouldCallFacade() {
        UUID accountId = UUID.randomUUID();
        UUID specialistId = UUID.randomUUID();
        UUID id = UUID.randomUUID();

        when(principal.getAccountId()).thenReturn(accountId);

        ResponseEntity<?> result = controller.delete(principal, specialistId.toString(), id.toString());

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(facade).delete(any(ReviewDeleteRequest.class));
    }

    @Test
    @DisplayName("UT: getAll() should call aggregator and return OK")
    void getAll_shouldCallAggregator() {
        UUID specialistId = UUID.randomUUID();
        ReviewSort sort = new ReviewSort(0, 10, true, null);
        PageResponse response = new PageResponse(Collections.emptyList(), 0);

        when(aggregator.findAllWithSortBySpecialistId(specialistId, sort)).thenReturn(response);

        ResponseEntity<?> result = controller.getAll(specialistId.toString(), sort);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(aggregator).findAllWithSortBySpecialistId(specialistId, sort);
    }
}
