package com.aidcompass.specialistdirectory.domain.specialist.services;

import com.aidcompass.specialistdirectory.domain.review.models.enums.RatingOperationType;
import com.aidcompass.specialistdirectory.domain.specialist.SpecialistMapper;
import com.aidcompass.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.aidcompass.specialistdirectory.domain.specialist.repositories.SpecialistRepository;
import com.aidcompass.specialistdirectory.domain.specialist.repositories.SpecialistSpecification;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;
import com.aidcompass.specialistdirectory.exceptions.SpecialistCreatorIdNotFoundByIdException;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.aidcompass.specialistdirectory.utils.pagination.PaginationUtils;
import com.aidcompass.specialistdirectory.domain.type.models.dtos.TypeCreateDto;
import com.aidcompass.specialistdirectory.domain.type.models.dtos.TypeResponseDto;
import com.aidcompass.specialistdirectory.domain.type.services.TypeService;
import com.aidcompass.specialistdirectory.domain.type.services.TypeConstants;
import com.aidcompass.specialistdirectory.exceptions.SpecialistNotFoundByIdException;
import com.aidcompass.specialistdirectory.utils.pagination.PageRequest;
import com.aidcompass.specialistdirectory.utils.pagination.PageResponse;
import com.aidcompass.specialistdirectory.utils.pagination.SpecificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UnifiedSpecialistService implements SpecialistService, SystemSpecialistService {

    private final SpecialistRepository repository;
    private final SpecificationRepository<SpecialistEntity> specificationRepository;
    private final SpecialistCountService countService;
    private final SpecialistMapper mapper;
    private final SpecialistCacheService cacheService;
    private final TypeService typeService;


    @Caching(
            evict = {@CacheEvict(value = "specialists:created:count:total", key = "#result.creatorId")},
            put = {@CachePut(value = "specialists", key = "#result.id + ':' + #result.creatorId")}
    )
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
        entity.setTotalRating(0.0);
        entity.setReviewsCount(0);
        entity = repository.save(entity);
        cacheService.putCreatorId(entity.getId(), entity.getCreatorId());
        cacheService.evictCreatedCountByFilter(entity.getCreatorId());
        return mapper.toResponseDto(entity);
    }

    @CachePut(value = "specialists", key = "#result.id + ':' + #result.creatorId")
    @Transactional
    @Override
    public SpecialistResponseDto update(SpecialistUpdateDto dto) {
        SpecialistEntity entity = repository.findWithTypeById(dto.getId()).orElseThrow(SpecialistNotFoundByIdException::new);
        // compare and early return ?
        Long inputTypeId = dto.getTypeId();
        Long existedTypeId = entity.getType().getId();
        if (!existedTypeId.equals(inputTypeId)) {
            if (!existedTypeId.equals(TypeConstants.OTHER_TYPE_ID) && inputTypeId.equals(TypeConstants.OTHER_TYPE_ID)) {
                saveSuggestedType(entity, dto.getCreatorId(), dto.getAnotherType());
            } else if (existedTypeId.equals(TypeConstants.OTHER_TYPE_ID)) {
                entity.setSuggestedTypeId(null);
            }
            entity.setType(typeService.getReferenceById(inputTypeId));
        } else if (existedTypeId.equals(TypeConstants.OTHER_TYPE_ID)) {
            TypeResponseDto typeDto = typeService.findSuggestedById(entity.getSuggestedTypeId());
            if (!typeDto.title().equalsIgnoreCase(dto.getAnotherType())) {
                saveSuggestedType(entity, dto.getCreatorId(), dto.getAnotherType());
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

    @Cacheable(value = "specialists:creator_id", key = "#id")
    @Transactional(readOnly = true)
    @Override
    public UUID getCreatorIdById(UUID id) {
        return repository.findCreatorIdById(id).orElseThrow(SpecialistCreatorIdNotFoundByIdException::new);
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

    @Transactional
    @Override
    public void updateAllByTypeIdPair(Long oldTypeId, Long newTypeId) {
        repository.updateAllByTypeTitle(oldTypeId, newTypeId);
    }

    @Transactional
    @Override
    public void updateRatingById(UUID id, long rating, RatingOperationType operationType) {
        SpecialistEntity entity = repository.findById(id).orElseThrow(SpecialistNotFoundByIdException::new);
        switch (operationType) {
            case PERSIST -> {
                long summaryRating = entity.getSummaryRating() + rating;
                long reviewRating = entity.getReviewsCount() + 1;
                double totalRating = (double) summaryRating / reviewRating;
                entity.setSummaryRating(summaryRating);
                entity.setReviewsCount(reviewRating);
                entity.setTotalRating(totalRating);
                repository.save(entity);
            }
            case UPDATE -> {
                long summaryRating = entity.getSummaryRating()  + rating;
                long reviewRating = entity.getReviewsCount();
                double totalRating = (double) summaryRating / reviewRating;
                entity.setSummaryRating(summaryRating);
                entity.setReviewsCount(reviewRating);
                entity.setTotalRating(totalRating);
                repository.save(entity);
            }
            case DELETE -> {
                long summaryRating = entity.getSummaryRating() + rating;
                long reviewRating = entity.getReviewsCount() - 1;
                double totalRating = (double) summaryRating / reviewRating;
                entity.setSummaryRating(summaryRating);
                entity.setReviewsCount(reviewRating);
                entity.setTotalRating(totalRating);
                repository.save(entity);
            }
        }
    }

    @CacheEvict(value = "specialists:creator_id", key = "#id")
    @Transactional
    @Override
    public void deleteById(UUID id) {
        UUID creatorId = cacheService.getCreatorId(id);
        if (creatorId == null) {
            creatorId = repository.findCreatorIdById(id).orElseThrow(SpecialistCreatorIdNotFoundByIdException::new);
        }
        repository.deleteById(id);
        cacheService.evictSpecialist(id, creatorId);
        cacheService.evictTotalCreatedCount(creatorId);
        cacheService.evictCreatedCountByFilter(creatorId);
    }

    @Cacheable(value = "specialists:all", key = "#page.cacheKey()", condition = "#page.pageNumber() < 3")
    @Transactional(readOnly = true)
    @Override
    public PageResponse<SpecialistResponseDto> findAll(PageRequest page) {
        Slice<SpecialistEntity> slice = specificationRepository.findAll(PaginationUtils.generatePageable(page));
        return new PageResponse<>(
                mapper.toResponseDtoList(slice.getContent()),
                (countService.countAll() + page.pageSize() - 1) / page.pageSize()
        );
    }

    @Cacheable(value = "specialists:filter", key = "#filter.cacheKey()", condition = "#filter.pageNumber() < 2")
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
    public PageResponse<SpecialistResponseDto> findAllByCreatorId(UUID creatorId, PageRequest page) {

        Specification<SpecialistEntity> specification = Specification
                .where(SpecialistSpecification.filterByCreatorId(creatorId));

        Slice<SpecialistEntity> slice = specificationRepository.findAll(
                specification, PaginationUtils.generatePageable(page)
        );

        return new PageResponse<>(
                mapper.toResponseDtoList(slice.getContent()),
                (countService.countByCreatorId(creatorId) + page.pageSize() - 1) / page.pageSize()
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

    @Override
    public SpecialistResponseDto toResponseDto(SpecialistEntity entity) {
        return mapper.toResponseDto(entity);
    }
}