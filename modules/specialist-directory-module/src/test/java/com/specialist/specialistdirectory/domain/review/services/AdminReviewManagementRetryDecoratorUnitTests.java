package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewAggregatedResponseDto;
import com.specialist.specialistdirectory.domain.review.models.filters.AdminReviewSort;
import com.specialist.specialistdirectory.exceptions.ReviewManageException;
import com.specialist.utils.pagination.PageResponse;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminReviewManagementRetryDecoratorUnitTests {

    @Mock
    private AdminReviewManagementFacade delegate;

    @InjectMocks
    private AdminReviewManagementRetryDecorator decorator;

    @Test
    @DisplayName("UT: getAll() should delegate")
    void getAll_shouldDelegate() {
        UUID specialistId = UUID.randomUUID();
        AdminReviewSort sort = mock(AdminReviewSort.class);
        PageResponse<ReviewAggregatedResponseDto> response = mock(PageResponse.class);

        when(delegate.getAll(specialistId, sort)).thenReturn(response);

        PageResponse<ReviewAggregatedResponseDto> result = decorator.getAll(specialistId, sort);

        assertEquals(response, result);
        verify(delegate).getAll(specialistId, sort);
    }

    @Test
    @DisplayName("UT: approve() should delegate")
    void approve_shouldDelegate() {
        UUID specialistId = UUID.randomUUID();
        UUID id = UUID.randomUUID();

        decorator.approve(specialistId, id);

        verify(delegate).approve(specialistId, id);
    }

    @Test
    @DisplayName("UT: delete() should delegate")
    void delete_shouldDelegate() {
        UUID specialistId = UUID.randomUUID();
        UUID id = UUID.randomUUID();

        decorator.delete(specialistId, id);

        verify(delegate).delete(specialistId, id);
    }

    @Test
    @DisplayName("UT: recover(OptimisticLockException) should throw ReviewManageException")
    void recover_optimisticLock_shouldThrow() {
        UUID specialistId = UUID.randomUUID();
        UUID id = UUID.randomUUID();

        assertThrows(ReviewManageException.class, () -> decorator.recover(new OptimisticLockException(), specialistId, id));
    }

    @Test
    @DisplayName("UT: recover(Exception) should throw ReviewManageException")
    void recover_exception_shouldThrow() {
        UUID specialistId = UUID.randomUUID();
        UUID id = UUID.randomUUID();

        assertThrows(ReviewManageException.class, () -> decorator.recover(new Exception(), specialistId, id));
    }
}
