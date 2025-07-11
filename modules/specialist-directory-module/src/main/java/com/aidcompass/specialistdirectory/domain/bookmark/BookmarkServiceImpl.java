package com.aidcompass.specialistdirectory.domain.bookmark;

import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.aidcompass.specialistdirectory.utils.PageRequest;
import com.aidcompass.specialistdirectory.utils.PageResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookmarkServiceImpl implements BookmarkService {

    @Override
    public SpecialistResponseDto save(UUID ownerId, UUID specialistId) {
        return null;
    }

    @Override
    public void deleteBySpecialistId(UUID ownerId, UUID specialistId) {

    }

    @Override
    public PageResponse<SpecialistResponseDto> findAllByOwnerId(UUID ownerId, PageRequest page) {
        return null;
    }

    @Override
    public PageResponse<SpecialistResponseDto> findAllByOwnerIdAndFilter(UUID ownerId, ExtendedSpecialistFilter filter) {
        return null;
    }
}
