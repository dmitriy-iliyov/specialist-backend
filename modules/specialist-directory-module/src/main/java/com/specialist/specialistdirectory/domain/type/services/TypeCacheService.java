package com.specialist.specialistdirectory.domain.type.services;

import com.specialist.specialistdirectory.domain.type.models.dtos.TypeResponseDto;

public interface TypeCacheService {
    void putToSuggestedType(TypeResponseDto dto);

    void evictSuggestedType(Long id);

    void evictSuggestedTypeId(String title, boolean isApproved);

    void totalEvictSuggestedType(Long id);

    void putToExists(Long id);
}
