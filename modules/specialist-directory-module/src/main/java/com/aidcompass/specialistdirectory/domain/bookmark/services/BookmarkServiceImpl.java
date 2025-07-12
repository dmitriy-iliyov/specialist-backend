package com.aidcompass.specialistdirectory.domain.bookmark.services;

import com.aidcompass.specialistdirectory.domain.bookmark.BookmarkEntity;
import com.aidcompass.specialistdirectory.domain.bookmark.BookmarkRepository;
import com.aidcompass.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.aidcompass.specialistdirectory.domain.specialist.services.SystemSpecialistService;
import com.aidcompass.specialistdirectory.utils.pagination.PageRequest;
import com.aidcompass.specialistdirectory.utils.pagination.PageResponse;
import com.aidcompass.specialistdirectory.utils.pagination.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final SystemSpecialistService specialistService;
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkCountService bookmarkCountService;


    @Transactional
    @Override
    public SpecialistResponseDto save(UUID ownerId, UUID specialistId) {
        SpecialistEntity specialistEntity = specialistService.findEntityById(specialistId);
        bookmarkRepository.save(new BookmarkEntity(ownerId, specialistEntity));
        return specialistService.toResponseDto(specialistEntity);
    }

    @Transactional
    @Override
    public void deleteBySpecialistId(UUID ownerId, UUID specialistId) {
        bookmarkRepository.deleteBySpecialist(specialistService.getReferenceById(specialistId));
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<SpecialistResponseDto> findAllByOwnerId(UUID ownerId, PageRequest page) {
        List<SpecialistEntity> entityList = bookmarkRepository
                .findAllByOwnerId(ownerId, PaginationUtils.generatePageable(page))
                .getContent().stream()
                .map(BookmarkEntity::getSpecialist)
                .toList();
        return new PageResponse<>(
                specialistService.toResponseDtoList(entityList),
                (bookmarkCountService.countByOwnerId(ownerId) + page.pageSize() - 1) / page.pageSize()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<SpecialistResponseDto> findAllByOwnerIdAndFilter(UUID ownerId, ExtendedSpecialistFilter filter) {
        return specialistService.findAllByFilterIn(filter, bookmarkCountService.findAllSpecialistIdByOwnerId(ownerId));
    }

    @Transactional
    @Override
    public void deleteAllByOwnerId(UUID ownerId) {
        bookmarkRepository.deleteAllByOwnerId(ownerId);
    }
}
