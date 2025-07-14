package com.aidcompass.specialistdirectory.domain.specialist_type.services;

import com.aidcompass.specialistdirectory.domain.specialist_type.TypeMapper;
import com.aidcompass.specialistdirectory.domain.specialist_type.TypeRepository;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.TypeEntity;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.ShortTypeDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeUpdateDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases.TypeCacheService;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases.TypeService;
import com.aidcompass.specialistdirectory.exceptions.NullOrBlankAnotherTypeException;
import com.aidcompass.specialistdirectory.exceptions.SpecialistTypeEntityNotFoundByIdException;
import com.aidcompass.specialistdirectory.exceptions.SpecialistTypeEntityNotFoundByTitleException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final TypeRepository repository;
    private final TypeMapper mapper;
    private final TypeCacheService cacheService;


    @CacheEvict(value = "specialists:types:approved:all", allEntries = true)
    @Transactional
    @Override
    public TypeDto save(TypeCreateDto dto) {
        TypeEntity entity = mapper.toEntity(dto);
        entity.setApproved(true);
        return mapper.toDto(repository.save(entity));
    }

    @Cacheable(value = "specialists:types:suggested:id", key = "#dto.getTitle()")
    @Transactional
    @Override
    public Long saveSuggested(TypeCreateDto dto) {
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new NullOrBlankAnotherTypeException();
        }
        Optional<TypeEntity> existedEntity = repository.findByTitle(dto.getTitle().toUpperCase());
        if (existedEntity.isEmpty()) {
            TypeEntity entity = mapper.toEntity(dto);
            entity.setApproved(false);
            entity = repository.save(entity);
            cacheService.putToSuggestedType(mapper.toDto(entity));
            return entity.getId();
        }
        return existedEntity.get().getId();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByTitleIgnoreCase(String title) {
        return repository.existsByTitleIgnoreCase(title);
    }

    @Cacheable(value = "specialists:types:suggested", key = "#id")
    @Transactional(readOnly = true)
    @Override
    public TypeDto findSuggestedById(Long id) {
        return mapper.toDto(repository.findByIdAndIsApproved(id, false).orElseThrow(SpecialistTypeEntityNotFoundByIdException::new));
    }

    @Transactional(readOnly = true)
    @Override
    public ShortTypeDto findByTitle(String title) {
        return mapper.toShortDto(repository.findByTitle(title).orElseThrow(
                SpecialistTypeEntityNotFoundByTitleException::new)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<TypeDto> findAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public TypeEntity getReferenceById(Long id) {
        return repository.getReferenceById(id);
    }

    @Cacheable(value = "specialists:types:approved:all", key = "'json'")
    @Transactional(readOnly = true)
    @Override
    public List<ShortTypeDto> findAllApprovedAsJson() {
        return repository.findAllByIsApproved(true).stream()
                .map(type -> new ShortTypeDto(type.getId(), type.getTitle()))
                .toList();
    }

    @Cacheable(value = "specialists:types:approved:all", key = "'map'")
    @Transactional(readOnly = true)
    @Override
    public Map<Long, String> findAllApprovedAsMap() {
        return repository.findAllByIsApproved(true).stream()
                .collect(Collectors.toMap(TypeEntity::getId, TypeEntity::getTitle));
    }

    @Transactional(readOnly = true)
    @Override
    public List<TypeDto> findAllUnapproved() {
        return mapper.toDtoList(repository.findAllByIsApproved(false));
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
    public TypeDto update(TypeUpdateDto dto) {
        TypeEntity entity = repository.findById(dto.getId()).orElseThrow(SpecialistTypeEntityNotFoundByIdException::new);
        String previousTitle = entity.getTitle();
        mapper.updateEntityFromDto(dto, entity);
        TypeDto resultDto = mapper.toDto(repository.save(entity));
        cacheService.evictSuggestedTypeId(previousTitle, resultDto.isApproved());
        return resultDto;
    }

    @CacheEvict(value = "specialists:types:approved:all", allEntries = true)
    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
        cacheService.totalEvictSuggestedType(id);
    }
}