package com.specialist.specialistdirectory.domain.specialist.models.filters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpecialistFilterUnitTests {

    @Test
    @DisplayName("UT: isEmpty() when all fields null should return true")
    void isEmpty_allNull_shouldReturnTrue() {
        SpecialistFilter filter = new SpecialistFilter(null, null, null, null, null, null, true, 0, 10);
        assertTrue(filter.isEmpty());
    }

    @Test
    @DisplayName("UT: isEmpty() when one field not null should return false")
    void isEmpty_oneNotNull_shouldReturnFalse() {
        SpecialistFilter filter = new SpecialistFilter("city", null, null, null, null, null, true, 0, 10);
        assertFalse(filter.isEmpty());
    }
}
