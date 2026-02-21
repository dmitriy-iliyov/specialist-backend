package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.review.models.enums.OperationType;
import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecialistRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpecialistRatingServiceImplUnitTests {

    @Mock
    private SpecialistRepository repository;

    @InjectMocks
    private SpecialistRatingServiceImpl service;

    @Test
    @DisplayName("UT: updateRatingById() for PERSIST should update rating and count")
    void updateRatingById_persist_shouldUpdate() {
        UUID id = UUID.randomUUID();
        SpecialistEntity entity = new SpecialistEntity();
        entity.setSummaryRating(10);
        entity.setReviewsCount(2);

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        service.updateRatingById(id, 5, OperationType.PERSIST);

        assertEquals(15, entity.getSummaryRating());
        assertEquals(3, entity.getReviewsCount());
        assertEquals(5.0, entity.getRating());
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("UT: updateRatingById() for UPDATE should update rating")
    void updateRatingById_update_shouldUpdate() {
        UUID id = UUID.randomUUID();
        SpecialistEntity entity = new SpecialistEntity();
        entity.setSummaryRating(10);
        entity.setReviewsCount(2);

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        service.updateRatingById(id, -1, OperationType.UPDATE);

        assertEquals(9, entity.getSummaryRating());
        assertEquals(2, entity.getReviewsCount());
        assertEquals(4.5, entity.getRating());
        verify(repository).save(entity);
    }

    @Test
    @DisplayName("UT: updateRatingById() for DELETE should update rating and count")
    void updateRatingById_delete_shouldUpdate() {
        UUID id = UUID.randomUUID();
        SpecialistEntity entity = new SpecialistEntity();
        entity.setSummaryRating(10);
        entity.setReviewsCount(2);

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        service.updateRatingById(id, -5, OperationType.DELETE);

        assertEquals(5, entity.getSummaryRating());
        assertEquals(1, entity.getReviewsCount());
        assertEquals(5.0, entity.getRating());
        verify(repository).save(entity);
    }
}
