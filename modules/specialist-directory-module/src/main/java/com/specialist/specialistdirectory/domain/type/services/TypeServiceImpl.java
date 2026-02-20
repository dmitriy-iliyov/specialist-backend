package com.specialist.specialistdirectory.domain.type.services;

import com.specialist.specialistdirectory.domain.type.TypeMapper;
import com.specialist.specialistdirectory.domain.type.TypeRepository;
import com.specialist.specialistdirectory.domain.type.models.TypeEntity;
import com.specialist.specialistdirectory.domain.type.models.dtos.ShortTypeResponseDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.TypeCreateDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.TypeResponseDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.TypeUpdateDto;
import com.specialist.specialistdirectory.exceptions.NullOrBlankAnotherTypeException;
import com.specialist.specialistdirectory.exceptions.SpecialistTypeEntityNotFoundByIdException;
import com.specialist.specialistdirectory.exceptions.SpecialistTypeEntityNotFoundByTitleException;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final TypeRepository repository;
    private final TypeMapper mapper;
    private final TypeCacheService cacheService;

    @CacheEvict(value = "specialists:types:approved:all", allEntries = true)
    @Transactional
    @Override
    public TypeResponseDto save(TypeCreateDto dto) {
        TypeEntity entity = mapper.toEntity(dto);
        entity.setApproved(true);
        TypeResponseDto resultDto = mapper.toDto(repository.save(entity));
        cacheService.putToExists(resultDto.id());
        return resultDto;
    }

    @Cacheable(value = "specialists:types:suggested:id", key = "#dto.getTitle()")
    @Transactional
    @Override
    public Long suggest(TypeCreateDto dto) {
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new NullOrBlankAnotherTypeException();
        }
        Optional<TypeEntity> existedEntity = repository.findByTitle(dto.getTitle().toUpperCase());
        Long id;
        if (existedEntity.isEmpty()) {
            TypeEntity entity = mapper.toEntity(dto);
            entity.setApproved(false);
            entity = repository.save(entity);
            id = entity.getId();
            cacheService.putToSuggestedType(mapper.toDto(entity));
            cacheService.putToExists(id);
            return id;
        }
        id = existedEntity.get().getId();
        cacheService.putToExists(id);
        return id;
    }

    @Cacheable(value = "specialists:types:exists", key = "#id")
    @Transactional(readOnly = true)
    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByTitleIgnoreCase(String title) {
        return repository.existsByTitleIgnoreCase(title);
    }

    @Transactional(readOnly = true)
    @Override
    public TypeEntity getReferenceById(Long id) {
        return repository.getReferenceById(id);
    }

    @Cacheable(value = "specialists:types:suggested", key = "#id")
    @Transactional(readOnly = true)
    @Override
    public TypeResponseDto findSuggestedById(Long id) {
        return mapper.toDto(repository.findByIdAndIsApproved(id, false).orElseThrow(SpecialistTypeEntityNotFoundByIdException::new));
    }

    @Transactional(readOnly = true)
    @Override
    public ShortTypeResponseDto findByTitle(String title) {
        return mapper.toShortDto(repository.findByTitle(title.toUpperCase()).orElseThrow(
                SpecialistTypeEntityNotFoundByTitleException::new)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<TypeResponseDto> findAll(PageRequest page) {
        Page<TypeEntity> entityPage = repository.findAll(this.generatePageable(page));
        return new PageResponse<>(
                mapper.toDtoList(entityPage.getContent()),
                entityPage.getTotalPages()
        );
    }

    @Cacheable(value = "specialists:types:approved:all", key = "'ids'")
    @Transactional(readOnly = true)
    @Override
    public List<Long> findAllApprovedIds() {
        return repository.findAllIdByIsApproved(true);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<TypeResponseDto> findAllUnapproved(PageRequest page) {
        Page<TypeEntity> entityPage = repository.findAllByIsApproved(
                false, this.generatePageable(page)
        );
        return new PageResponse<>(
                mapper.toDtoList(entityPage.getContent()),
                entityPage.getTotalPages()
        );
    }

    @Caching(evict = {
            @CacheEvict(value = "specialists:types:approved:all", allEntries = true),
            @CacheEvict(value = "specialists:types:suggested:id", key = "#result")}
    )
    @Transactional
    @Override
    public String approve(Long id) {
        TypeEntity entity = repository.findById(id).orElseThrow(SpecialistTypeEntityNotFoundByIdException::new);
        entity.setApproved(true);
        entity = repository.save(entity);
        cacheService.evictSuggestedType(entity.getId());
        return entity.getTitle();
    }

    @Caching(evict = {
            @CacheEvict(value = "specialists:types:approved:all", allEntries = true),
            @CacheEvict(value = "specialists:types:suggested", key = "#result.id()")}
    )
    @Transactional
    @Override
    public TypeResponseDto update(TypeUpdateDto dto) {
        TypeEntity entity = repository.findById(dto.getId()).orElseThrow(SpecialistTypeEntityNotFoundByIdException::new);
        String previousTitle = entity.getTitle();
        mapper.updateEntityFromDto(dto, entity);
        TypeResponseDto resultDto = mapper.toDto(repository.save(entity));
        cacheService.evictSuggestedTypeId(previousTitle, resultDto.isApproved());
        return resultDto;
    }

    @Caching(evict = {
            @CacheEvict(value = "specialists:types:approved:all", allEntries = true),
            @CacheEvict(value = "specialists:types:exists", key = "#id")}
    )
    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
        cacheService.totalEvictSuggestedType(id);
    }

    private Pageable generatePageable(PageRequest page) {
        return org.springframework.data.domain.PageRequest.of(
                page.getPageNumber(), page.getPageSize(), Sort.by("id").ascending());
    }
}