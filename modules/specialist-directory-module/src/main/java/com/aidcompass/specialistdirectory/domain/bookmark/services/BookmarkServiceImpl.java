package com.aidcompass.specialistdirectory.domain.bookmark.services;

import com.aidcompass.specialistdirectory.domain.bookmark.BookmarkEntity;
import com.aidcompass.specialistdirectory.domain.bookmark.BookmarkRepository;
import com.aidcompass.specialistdirectory.domain.bookmark.services.interfases.BookmarkCountService;
import com.aidcompass.specialistdirectory.domain.bookmark.services.interfases.BookmarkService;
import com.aidcompass.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.aidcompass.specialistdirectory.domain.specialist.services.interfaces.SystemSpecialistService;
import com.aidcompass.specialistdirectory.utils.pagination.PageRequest;
import com.aidcompass.specialistdirectory.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final SystemSpecialistService specialistService;
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkCountService bookmarkCountService;


    @Caching(evict = {
            @CacheEvict(value = "specialists:bookmarks:count:total", key = "#ownerId"),
            @CacheEvict(value = "specialists:bookmarks:specialist_ids", key = "#ownerId")}
    )
    @Transactional
    @Override
    public SpecialistResponseDto save(UUID ownerId, UUID specialistId) {
        SpecialistEntity specialistEntity = specialistService.findEntityById(specialistId);
        bookmarkRepository.save(new BookmarkEntity(ownerId, specialistEntity));
        return specialistService.toResponseDto(specialistEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByOwnerIdAndSpecialistId(UUID ownerId, UUID specialistId) {
        return bookmarkRepository.existsByOwnerIdAndSpecialistId(ownerId, specialistId);
    }

    @Caching(evict = {
            @CacheEvict(value = "specialists:bookmarks:count:total", key = "#ownerId"),
            @CacheEvict(value = "specialists:bookmarks:specialist_ids", key = "#ownerId")}
    )
    @Transactional
    @Override
    public void deleteById(UUID ownerId, UUID id) {
        bookmarkRepository.deleteBySpecialist(specialistService.getReferenceById(id));
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<SpecialistResponseDto> findAllByOwnerId(UUID ownerId, PageRequest page) {
        List<SpecialistEntity> entityList = bookmarkRepository
                .findAllByOwnerId(ownerId, Pageable.ofSize(page.pageSize()).withPage(page.pageNumber()))
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
        return specialistService.findAllByFilterAndIdIn(filter, bookmarkCountService.findAllSpecialistIdByOwnerId(ownerId));
    }

    @Caching(evict = {
            @CacheEvict(value = "specialists:bookmarks:count:total", key = "#ownerId"),
            @CacheEvict(value = "specialists:bookmarks:specialist_ids", key = "#ownerId")}
    )
    @Transactional
    @Override
    public void deleteAllByOwnerId(UUID ownerId) {
        bookmarkRepository.deleteAllByOwnerId(ownerId);
    }
}
