package com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases;

import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeDto;

public interface TypeCacheService {
    void putToSuggestedType(TypeDto dto);

    void evictSuggestedType(Long id);

    void evictSuggestedTypeId(String title, boolean isApproved);

    void totalEvictSuggestedType(Long id);
}
