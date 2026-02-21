package com.specialist.specialistdirectory.infrastructure;

import com.specialist.specialistdirectory.domain.type.TypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TypeDbInitializerUnitTests {

    @Mock
    private TypeRepository repository;

    @InjectMocks
    private TypeDbInitializer initializer;

    @Test
    @DisplayName("UT: setUp() when repository empty should save defaults")
    void setUp_emptyRepo_shouldSave() {
        when(repository.count()).thenReturn(0L);

        initializer.setUp();

        verify(repository).saveAll(any(List.class));
    }

    @Test
    @DisplayName("UT: setUp() when repository not empty should do nothing")
    void setUp_notEmptyRepo_shouldDoNothing() {
        when(repository.count()).thenReturn(5L);

        initializer.setUp();

        verify(repository, never()).saveAll(any());
    }
}
