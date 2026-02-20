package com.specialist.specialistdirectory.ut.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewCreateRequest;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewDeleteRequest;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewResponseDto;
import com.specialist.specialistdirectory.domain.review.models.dtos.ReviewUpdateRequest;
import com.specialist.specialistdirectory.domain.review.services.ReviewManagementFacade;
import com.specialist.specialistdirectory.domain.review.services.ReviewManagementRetryDecorator;
import com.specialist.specialistdirectory.exceptions.ReviewManageException;
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
class ReviewManagementRetryDecoratorUnitTests {

    @Mock
    private ReviewManagementFacade delegate;

    @InjectMocks
    private ReviewManagementRetryDecorator decorator;

    @Test
    @DisplayName("UT: save() should delegate")
    void save_shouldDelegate() {
        ReviewCreateRequest request = new ReviewCreateRequest(UUID.randomUUID(), UUID.randomUUID(), "payload", null);
        ReviewResponseDto response = mock(ReviewResponseDto.class);
        when(delegate.save(request)).thenReturn(response);

        ReviewResponseDto result = decorator.save(request);

        assertEquals(response, result);
        verify(delegate).save(request);
    }

    @Test
    @DisplayName("UT: update() should delegate")
    void update_shouldDelegate() {
        ReviewUpdateRequest request = new ReviewUpdateRequest(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "payload", null);
        ReviewResponseDto response = mock(ReviewResponseDto.class);
        when(delegate.update(request)).thenReturn(response);

        ReviewResponseDto result = decorator.update(request);

        assertEquals(response, result);
        verify(delegate).update(request);
    }

    @Test
    @DisplayName("UT: delete() should delegate")
    void delete_shouldDelegate() {
        ReviewDeleteRequest request = new ReviewDeleteRequest(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        decorator.delete(request);
        verify(delegate).delete(request);
    }

    @Test
    @DisplayName("UT: recover(save) should throw ReviewManageException")
    void recover_save_shouldThrow() {
        ReviewCreateRequest request = new ReviewCreateRequest(UUID.randomUUID(), UUID.randomUUID(), "payload", null);
        assertThrows(ReviewManageException.class, () -> decorator.recover(new OptimisticLockException(), request));
    }

    @Test
    @DisplayName("UT: recover(update) should throw ReviewManageException")
    void recover_update_shouldThrow() {
        ReviewUpdateRequest request = new ReviewUpdateRequest(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "payload", null);
        assertThrows(ReviewManageException.class, () -> decorator.recover(new OptimisticLockException(), request));
    }

    @Test
    @DisplayName("UT: recover(delete) should throw ReviewManageException")
    void recover_delete_shouldThrow() {
        ReviewDeleteRequest request = new ReviewDeleteRequest(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        assertThrows(ReviewManageException.class, () -> decorator.recover(new OptimisticLockException(), request));
    }
}
