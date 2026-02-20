package com.specialist.specialistdirectory.ut.domain.review.infrastructure;

import com.specialist.specialistdirectory.domain.review.infrastructure.ReviewWarmUpService;
import com.specialist.specialistdirectory.domain.review.services.ReviewAggregator;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewWarmUpServiceUnitTests {

    @Mock
    private CacheManager cacheManager;

    @Mock
    private SpecialistService specialistService;

    @Mock
    private ReviewAggregator reviewAggregator;

    @InjectMocks
    private ReviewWarmUpService service;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "page", 1);
        ReflectionTestUtils.setField(service, "pageSize", 10);
    }

    @Test
    @DisplayName("UT: warmUp() should execute warmUpPart 8 times")
    void warmUp_shouldExecuteParts() {
        Cache cache = mock(Cache.class);
        when(cacheManager.getCache("reviews")).thenReturn(cache);
        
        SpecialistResponseDto specialistDto = mock(SpecialistResponseDto.class);
        when(specialistDto.getId()).thenReturn(UUID.randomUUID());
        PageResponse<SpecialistResponseDto> pageResponse = new PageResponse<>(List.of(specialistDto), 1);
        
        when(specialistService.findAll(any(PageRequest.class))).thenReturn(pageResponse);
        when(reviewAggregator.findAllWithSortBySpecialistId(any(), any())).thenReturn(mock(PageResponse.class));

        service.warmUp();

        // 8 combinations * 1 page * 1 specialist = 8 puts
        verify(cache, times(8)).put(anyString(), any());
    }

    @Test
    @DisplayName("UT: warmUp() when cache missing should do nothing")
    void warmUp_cacheMissing_shouldDoNothing() {
        when(cacheManager.getCache("reviews")).thenReturn(null);

        service.warmUp();

        verify(specialistService, never()).findAll(any(PageRequest.class));
    }

    @Test
    @DisplayName("UT: warmUp() when exception occurs should log and continue/finish")
    void warmUp_exception_shouldLog() {
        when(cacheManager.getCache("reviews")).thenThrow(new RuntimeException("Cache error"));

        service.warmUp();

        // Should not throw exception
    }
}
