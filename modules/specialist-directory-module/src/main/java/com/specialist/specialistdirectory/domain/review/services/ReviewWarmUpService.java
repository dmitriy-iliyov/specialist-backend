package com.specialist.specialistdirectory.domain.review.services;

import com.specialist.specialistdirectory.domain.review.models.enums.SortType;
import com.specialist.specialistdirectory.domain.review.models.filters.ReviewSort;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.services.SpecialistService;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("prod-cache-warm-up")
@Service
@RequiredArgsConstructor
@Slf4j
public final class ReviewWarmUpService {

    @Value("${}")
    private Integer page;

    @Value("${}")
    private Integer pageSize;

    private final CacheManager cacheManager;
    private final SpecialistService specialistService;
    private final ReviewAggregator reviewAggregator;

    @PostConstruct
    public void warmUp() {
        try {
            warmUpPart(true, SortType.c_at, true);
            warmUpPart(true, SortType.c_at, false);
            warmUpPart(false, SortType.c_at, true);
            warmUpPart(false, SortType.c_at, false);
            warmUpPart(true, SortType.r, true);
            warmUpPart(true, SortType.r, false);
            warmUpPart(false, SortType.r, true);
            warmUpPart(false, SortType.r, false);
            log.info("Reviews cache successfully wormed up");
        } catch (Exception e) {
            log.error("Error when warming up reviews cache");
        }
    }

    public void warmUpPart(boolean pageRequestAsc, SortType sortType, boolean sortAsc) {
        Cache cache = cacheManager.getCache("reviews");
        if (cache != null) {
            for (int i = 1; i <= page; i++) {
                PageRequest pageRequest = new PageRequest(i, pageSize, pageRequestAsc);
                PageResponse<SpecialistResponseDto> specialistPage = specialistService.findAll(pageRequest);
                for (SpecialistResponseDto dto: specialistPage.data()) {
                    ReviewSort sort = new ReviewSort(sortType, sortAsc, i, pageSize);
                    String key = dto.getId().toString() + ":" + sort.cacheKey();
                    cache.put(key, reviewAggregator.findAllWithSortBySpecialistId(dto.getId(), sort));
                }
            }
        }
    }
}
