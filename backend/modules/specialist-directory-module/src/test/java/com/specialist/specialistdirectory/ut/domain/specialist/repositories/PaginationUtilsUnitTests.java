package com.specialist.specialistdirectory.ut.domain.specialist.repositories;

import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.repositories.PaginationUtils;
import com.specialist.specialistdirectory.exceptions.UnexpectedSpecialistFilterStateException;
import com.specialist.utils.pagination.PageDataHolder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PaginationUtilsUnitTests {

    @Test
    @DisplayName("UT: generateSpecification(ExtendedSpecialistFilter) with cityCode should return spec")
    void generateSpecification_cityCode_shouldReturnSpec() {
        ExtendedSpecialistFilter filter = mock(ExtendedSpecialistFilter.class);
        when(filter.getCityCode()).thenReturn("12345");

        Specification<SpecialistEntity> spec = PaginationUtils.generateSpecification(filter);
        assertNotNull(spec);
    }

    @Test
    @DisplayName("UT: generateSpecification(ExtendedSpecialistFilter) with city should return spec")
    void generateSpecification_city_shouldReturnSpec() {
        ExtendedSpecialistFilter filter = mock(ExtendedSpecialistFilter.class);
        when(filter.getCityCode()).thenReturn(null);
        when(filter.getCity()).thenReturn("City");

        Specification<SpecialistEntity> spec = PaginationUtils.generateSpecification(filter);
        assertNotNull(spec);
    }

    @Test
    @DisplayName("UT: generateSpecification(ExtendedSpecialistFilter) with no city/code should throw exception")
    void generateSpecification_noCityOrCode_shouldThrow() {
        ExtendedSpecialistFilter filter = mock(ExtendedSpecialistFilter.class);
        when(filter.getCityCode()).thenReturn(null);
        when(filter.getCity()).thenReturn(null);

        assertThrows(UnexpectedSpecialistFilterStateException.class, () -> PaginationUtils.generateSpecification(filter));
    }

    @Test
    @DisplayName("UT: generatePageable() asc true")
    void generatePageable_ascTrue() {
        PageDataHolder holder = mock(PageDataHolder.class);
        when(holder.isAsc()).thenReturn(true);
        when(holder.getPageNumber()).thenReturn(0);
        when(holder.getPageSize()).thenReturn(10);

        Pageable pageable = PaginationUtils.generatePageable(holder);
        
        assertEquals(Sort.by("rating").ascending(), pageable.getSort());
    }

    @Test
    @DisplayName("UT: generatePageable() asc false")
    void generatePageable_ascFalse() {
        PageDataHolder holder = mock(PageDataHolder.class);
        when(holder.isAsc()).thenReturn(false);
        when(holder.getPageNumber()).thenReturn(0);
        when(holder.getPageSize()).thenReturn(10);

        Pageable pageable = PaginationUtils.generatePageable(holder);

        assertEquals(Sort.by("rating").descending(), pageable.getSort());
    }
    
    @Test
    @DisplayName("UT: generatePageable() asc null")
    void generatePageable_ascNull() {
        PageDataHolder holder = mock(PageDataHolder.class);
        when(holder.isAsc()).thenReturn(null);
        when(holder.getPageNumber()).thenReturn(0);
        when(holder.getPageSize()).thenReturn(10);

        Pageable pageable = PaginationUtils.generatePageable(holder);

        assertEquals(Sort.by("rating").descending(), pageable.getSort());
    }
}
