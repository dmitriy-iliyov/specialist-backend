package com.specialist.specialistdirectory.domain.bookmark.services;

import com.specialist.specialistdirectory.domain.bookmark.BookmarkRepository;
import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkCreateDto;
import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkEntity;
import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkIdPair;
import com.specialist.specialistdirectory.domain.bookmark.models.BookmarkResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.services.SystemSpecialistService;
import com.specialist.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final SystemSpecialistService specialistService;
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkCountService bookmarkCountService;

    @Caching(evict = {
            @CacheEvict(value = "specialists:bookmarks:count:total", key = "#dto.getOwnerId()"),
            @CacheEvict(value = "specialists:bookmarks:id_pairs", key = "#dto.getOwnerId()")}
    )
    @Transactional
    @Override
    public BookmarkResponseDto save(BookmarkCreateDto dto) {
        SpecialistEntity specialistEntity = specialistService.findEntityById(dto.getSpecialistId());
        BookmarkEntity bookmarkEntity = bookmarkRepository.save(new BookmarkEntity(dto.getOwnerId(), specialistEntity));
        return new BookmarkResponseDto(bookmarkEntity.getId(), specialistService.toResponseDto(specialistEntity));
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByOwnerIdAndSpecialistId(UUID ownerId, UUID specialistId) {
        return bookmarkRepository.existsByOwnerIdAndSpecialistId(ownerId, specialistId);
    }

    @Caching(evict = {
            @CacheEvict(value = "specialists:bookmarks:count:total", key = "#ownerId"),
            @CacheEvict(value = "specialists:bookmarks:id_pairs", key = "#ownerId")}
    )
    @Transactional
    @Override
    public void deleteById(UUID ownerId, UUID id) {
        bookmarkRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<BookmarkResponseDto> findAllByOwnerIdAndFilter(UUID ownerId, ExtendedSpecialistFilter filter) {
        Map<UUID, UUID> idsMap = bookmarkCountService.findAllIdPairByOwnerId(ownerId).stream()
                .collect(Collectors.toMap(BookmarkIdPair::specialistId, BookmarkIdPair::id));
        Page<SpecialistEntity> specialistEntityPage = specialistService.findAllByFilterAndIdIn(
                filter, idsMap.keySet().stream().toList()
        );
        List<BookmarkResponseDto> dtoList = specialistEntityPage.getContent().stream()
                .map(specialistEntity -> new BookmarkResponseDto(
                        idsMap.get(specialistEntity.getId()), specialistService.toResponseDto(specialistEntity))
                )
                .toList();
        return new PageResponse<>(
                dtoList,
                specialistEntityPage.getTotalPages()
        );
    }

    @Caching(evict = {
            @CacheEvict(value = "specialists:bookmarks:count:total", key = "#ownerId"),
            @CacheEvict(value = "specialists:bookmarks:id_pairs", key = "#ownerId")}
    )
    @Transactional
    @Override
    public void deleteAllByOwnerId(UUID ownerId) {
        bookmarkRepository.deleteAllByOwnerId(ownerId);
    }
}
