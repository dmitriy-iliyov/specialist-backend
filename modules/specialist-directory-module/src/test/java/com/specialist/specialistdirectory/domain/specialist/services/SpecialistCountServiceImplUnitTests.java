package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecialistRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialistCountServiceImplUnitTests {

    @Mock
    private SpecialistRepository repository;

    @InjectMocks
    private SpecialistCountServiceImpl service;

    @Test
    @DisplayName("UT: countAll() should call repository with specification")
    void countAll_shouldCallRepository() {
        when(repository.count(any(Specification.class))).thenReturn(10L);

        long result = service.countAll();

        assertEquals(10L, result);
        verify(repository).count(any(Specification.class));
    }

    @Test
    @DisplayName("UT: countByFilter() should call repository with specification")
    void countByFilter_shouldCallRepository() {
        SpecialistFilter filter = mock(SpecialistFilter.class);
        when(repository.count(any(Specification.class))).thenReturn(5L);

        long result = service.countByFilter(filter);

        assertEquals(5L, result);
        verify(repository).count(any(Specification.class));
    }

    @Test
    @DisplayName("UT: countByCreatorId() should call repository")
    void countByCreatorId_shouldCallRepository() {
        UUID creatorId = UUID.randomUUID();
        when(repository.countByCreatorId(creatorId)).thenReturn(3L);

        long result = service.countByCreatorId(creatorId);

        assertEquals(3L, result);
        verify(repository).countByCreatorId(creatorId);
    }
}
