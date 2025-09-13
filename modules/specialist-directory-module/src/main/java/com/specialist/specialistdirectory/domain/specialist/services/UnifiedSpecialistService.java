package com.specialist.specialistdirectory.domain.specialist.services;

import com.specialist.specialistdirectory.domain.specialist.mappers.SpecialistMapper;
import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.SpecialistInfoEntity;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.*;
import com.specialist.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;
import com.specialist.specialistdirectory.domain.specialist.repositories.PaginationUtils;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecialistRepository;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecialistSpecification;
import com.specialist.specialistdirectory.domain.specialist.repositories.SpecificationRepository;
import com.specialist.specialistdirectory.domain.type.models.dtos.TypeCreateDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.TypeResponseDto;
import com.specialist.specialistdirectory.domain.type.services.TypeConstants;
import com.specialist.specialistdirectory.domain.type.services.TypeService;
import com.specialist.specialistdirectory.exceptions.SpecialistNotFoundByIdException;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UnifiedSpecialistService implements SpecialistService, SystemSpecialistService {

    private final SpecialistRepository repository;
    private final SpecificationRepository<SpecialistEntity> specificationRepository;
    private final SpecialistCountService countService;
    private final SpecialistMapper mapper;
    private final SpecialistCacheService cacheService;
    private final TypeService typeService;

    public UnifiedSpecialistService(SpecialistRepository repository,
                                    @Qualifier("specialistSpecificationRepository")
                                    SpecificationRepository<SpecialistEntity> specificationRepository,
                                    SpecialistCountService countService, SpecialistMapper mapper,
                                    SpecialistCacheService cacheService, TypeService typeService) {
        this.repository = repository;
        this.specificationRepository = specificationRepository;
        this.countService = countService;
        this.mapper = mapper;
        this.cacheService = cacheService;
        this.typeService = typeService;
    }

    @CachePut(value = "specialists", key = "#result.id + ':' + #result.creatorId")
    @Transactional
    @Override
    public SpecialistResponseDto save(SpecialistCreateDto dto) {
        SpecialistEntity entity = mapper.toEntity(dto);
        Long typeId = dto.getTypeId();
        if (typeId.equals(TypeConstants.OTHER_TYPE_ID)) {
            saveSuggestedType(entity, dto.getCreatorId(), dto.getAnotherType());
        }
        entity.setType(typeService.getReferenceById(typeId));
        entity.setSummaryRating(0);
        entity.setRating(0.0);
        entity.setReviewsCount(0);
        entity.setOwnerId(dto.getCreatorId());
        entity.setInfo(new SpecialistInfoEntity(dto.getCreatorType()));
        entity = repository.save(entity);
        cacheService.putShortInfo(
                entity.getId(),
                new ShortSpecialistInfo(entity.getCreatorId(), entity.getOwnerId(), dto.getStatus())
        );
        cacheService.evictCreatedCountByFilter(entity.getCreatorId());
        return mapper.toResponseDto(entity);
    }

    @CachePut(value = "specialists", key = "#result.id + ':' + #result.creatorId")
    @Transactional
    @Override
    public SpecialistResponseDto update(SpecialistUpdateDto dto) {
        SpecialistEntity entity = repository.findWithTypeById(dto.getId()).orElseThrow(SpecialistNotFoundByIdException::new);
        Long inputTypeId = dto.getTypeId();
        Long existedTypeId = entity.getType().getId();
        if (!existedTypeId.equals(inputTypeId)) {
            if (!existedTypeId.equals(TypeConstants.OTHER_TYPE_ID) && inputTypeId.equals(TypeConstants.OTHER_TYPE_ID)) {
                saveSuggestedType(entity, dto.getAccountId(), dto.getAnotherType());
            } else if (existedTypeId.equals(TypeConstants.OTHER_TYPE_ID)) {
                entity.setSuggestedTypeId(null);
            }
            entity.setType(typeService.getReferenceById(inputTypeId));
        } else if (existedTypeId.equals(TypeConstants.OTHER_TYPE_ID)) {
            TypeResponseDto typeDto = typeService.findSuggestedById(entity.getSuggestedTypeId());
            if (!typeDto.title().equalsIgnoreCase(dto.getAnotherType())) {
                saveSuggestedType(entity, dto.getAccountId(), dto.getAnotherType());
            }
        }
        mapper.updateEntityFromDto(dto, entity);
        entity = repository.save(entity);
        cacheService.evictCreatedCountByFilter(entity.getCreatorId());
        return mapper.toResponseDto(entity);
    }

    private void saveSuggestedType(SpecialistEntity entity, UUID creatorId, String suggestedType) {
        Long id = typeService.saveSuggested(new TypeCreateDto(creatorId, suggestedType.strip()));
        entity.setSuggestedTypeId(id);
    }

    @Transactional
    @Override
    public void updateAllByTypeIdPair(Long oldTypeId, Long newTypeId) {
        repository.updateAllByTypeTitle(oldTypeId, newTypeId);
    }

    @Transactional(readOnly = true)
    @Override
    public ShortSpecialistInfo getShortInfoById(UUID id) {
        ShortSpecialistInfo info = cacheService.getShortInfo(id);;
        if (info == null) {
            info = repository.findShortInfoById(id).orElseThrow(SpecialistNotFoundByIdException::new);
            cacheService.putShortInfo(id, info);
        }
        return info;
    }

    @Cacheable(value = "specialists", key = "#id + ':' + #creatorId")
    @Transactional(readOnly = true)
    @Override
    public SpecialistResponseDto findByCreatorIdAndId(UUID creatorId, UUID id) {
        SpecialistEntity entity = repository.findWithTypeById(id).orElseThrow(
                SpecialistNotFoundByIdException::new
        );
        SpecialistResponseDto dto = mapper.toResponseDto(entity);
        if (entity.getSuggestedTypeId() != null) {
            if (!entity.getCreatorId().equals(creatorId)) {
                dto.setAnotherType(null);
            } else {
                dto.setAnotherType(typeService.findSuggestedById(entity.getSuggestedTypeId()).title());
            }
        }
        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public SpecialistResponseDto findById(UUID id) {
        SpecialistEntity entity = repository.findWithTypeById(id).orElseThrow(
                SpecialistNotFoundByIdException::new
        );
        SpecialistResponseDto dto = mapper.toResponseDto(entity);
        if (entity.getSuggestedTypeId() != null) {
            dto.setAnotherType(typeService.findSuggestedById(entity.getSuggestedTypeId()).title());
        }
        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public SpecialistEntity findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(SpecialistNotFoundByIdException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public SpecialistEntity getReferenceById(UUID id) {
        return repository.getReferenceById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<SpecialistResponseDto> findAll(PageRequest page) {
        Specification<SpecialistEntity> specification = Specification.where(
                SpecialistSpecification.filterByApprovedAndManaged()
        );
        Slice<SpecialistEntity> slice = specificationRepository.findAll(
                specification, PaginationUtils.generatePageable(page)
        );
        return new PageResponse<>(
                mapper.toResponseDtoList(slice.getContent()),
                (countService.countAll() + page.pageSize() - 1) / page.pageSize()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<SpecialistResponseDto> findAllByFilter(SpecialistFilter filter) {
        Slice<SpecialistEntity> slice = specificationRepository.findAll(
                PaginationUtils.generateSpecification(filter), PaginationUtils.generatePageable(filter)
        );
        return new PageResponse<>(
                mapper.toResponseDtoList(slice.getContent()),
                (countService.countByFilter(filter) + filter.pageSize() - 1) / filter.pageSize()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<SpecialistResponseDto> findAllByCreatorIdAndFilter(UUID creatorId, ExtendedSpecialistFilter filter) {
        Specification<SpecialistEntity> specification = PaginationUtils.generateSpecification(filter)
                .and(SpecialistSpecification.filterByCreatorId(creatorId));
        Slice<SpecialistEntity> slice = specificationRepository.findAll(
                specification, PaginationUtils.generatePageable(filter)
        );
        return new PageResponse<>(
                mapper.toResponseDtoList(slice.getContent()),
                (countService.countByCreatorIdAndFilter(creatorId, filter) + filter.pageSize() - 1) / filter.pageSize()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Page<SpecialistEntity> findAllByFilterAndIdIn(ExtendedSpecialistFilter filter, List<UUID> ids) {
        Specification<SpecialistEntity> specification = PaginationUtils.generateSpecification(filter)
                .and(SpecialistSpecification.filterByIdIn(ids));
        return repository.findAll(specification, PaginationUtils.generatePageable(filter));
    }

    @CacheEvict(value = "specialists:short-info", key = "#id")
    @Transactional
    @Override
    public void deleteById(UUID id) {
        UUID creatorId = cacheService.getShortInfo(id).creatorId();
        if (creatorId == null) {
            creatorId = repository.findCreatorIdById(id).orElseThrow(SpecialistNotFoundByIdException::new);
        }
        repository.deleteById(id);
        cacheService.evictSpecialist(id, creatorId);
        cacheService.evictTotalCreatedCount(creatorId);
        cacheService.evictCreatedCountByFilter(creatorId);
    }

    @Override
    public BookmarkSpecialistResponseDto toResponseDto(SpecialistEntity entity) {
        return mapper.toBookmarkResponseDto(entity);
    }
}