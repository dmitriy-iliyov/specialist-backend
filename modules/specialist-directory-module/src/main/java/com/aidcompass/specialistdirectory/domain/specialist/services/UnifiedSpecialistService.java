package com.aidcompass.specialistdirectory.domain.specialist.services;

import com.aidcompass.specialistdirectory.domain.specialist.SpecialistMapper;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.*;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.ExtendedSpecialistFilter;
import com.aidcompass.specialistdirectory.domain.specialist.models.filters.SpecialistFilter;
import com.aidcompass.specialistdirectory.domain.specialist.repositories.SpecialistRepository;
import com.aidcompass.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.aidcompass.specialistdirectory.domain.specialist.repositories.SpecialistSpecification;
import com.aidcompass.specialistdirectory.utils.pagination.PaginationUtils;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.TypeService;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.TypeConstants;
import com.aidcompass.specialistdirectory.exceptions.SpecialistNotFoundByIdException;
import com.aidcompass.specialistdirectory.utils.pagination.PageRequest;
import com.aidcompass.specialistdirectory.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UnifiedSpecialistService implements SpecialistService, SystemSpecialistService {

    private final SpecialistRepository specialistRepository;
    private final SpecialistCountService countService;
    private final SpecialistMapper mapper;
    private final TypeService typeService;
    private final CacheManager cacheManager;


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
        entity.setTotalRating(0.0);
        entity.setReviewsCount(0);
        entity = specialistRepository.save(entity);
        Cache cache = cacheManager.getCache("specialists:ownership");
        if (cache != null) {
            cache.put(entity.getId().toString(), entity.getCreatorId().toString());
        }
        return mapper.toResponseDto(entity);
    }

    @CachePut(value = "specialists", key = "#result.id + ':' + #result.creatorId")
    @Transactional
    @Override
    public SpecialistResponseDto update(SpecialistUpdateDto dto) {
        SpecialistEntity entity = specialistRepository.findWithTypeById(dto.getId()).orElseThrow(SpecialistNotFoundByIdException::new);
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
            TypeDto typeDto = typeService.findSuggestedById(entity.getSuggestedTypeId());
            if (!typeDto.title().equalsIgnoreCase(dto.getAnotherType())) {
                saveSuggestedType(entity, dto.getCreatorId(), dto.getAnotherType());
            }
        }
        mapper.updateEntityFromDto(dto, entity);
        entity = specialistRepository.save(entity);
        return mapper.toResponseDto(entity);
    }

    private void saveSuggestedType(SpecialistEntity entity, UUID creatorId, String suggestedType) {
        Long id = typeService.saveSuggested(new TypeCreateDto(creatorId, suggestedType.strip()));
        entity.setSuggestedTypeId(id);
    }

    @Cacheable(value = "specialists:ownership", key = "#id")
    @Transactional(readOnly = true)
    @Override
    public UUID getCreatorIdById(UUID id) {
        return specialistRepository.findCreatorIdById(id).orElseThrow(SpecialistNotFoundByIdException::new);
    }

    @Cacheable(value = "specialists", key = "#id + ':' + #creatorId")
    @Transactional(readOnly = true)
    @Override
    public SpecialistResponseDto findByCreatorIdAndId(UUID creatorId, UUID id) {
        SpecialistEntity entity = specialistRepository.findWithTypeById(id).orElseThrow(
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
        SpecialistEntity entity = specialistRepository.findWithTypeById(id).orElseThrow(
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
        return specialistRepository.findById(id).orElseThrow(SpecialistNotFoundByIdException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public SpecialistEntity getReferenceById(UUID id) {
        return specialistRepository.getReferenceById(id);
    }

    @Transactional
    @Override
    public void updateAllByTypeIdPair(Long oldTypeId, Long newTypeId) {
        specialistRepository.updateAllByTypeTitle(oldTypeId, newTypeId);
    }

    // invalidate specialists ?
    @Cacheable(value = "specialists:ownership", key = "#id")
    @Transactional
    @Override
    public void deleteById(UUID id) {
        specialistRepository.deleteById(id);
//        Cache cache = cacheManager.getCache("specialists");
//        if (cache != null) {
//            cache.evict();
//        }
//        cache = cacheManager.getCache("specialists:created:all");
    }

    //@Cacheable(value = "specialists:all", condition = "#page.pageNumber() < 3")
    @Transactional(readOnly = true)
    @Override
    public PageResponse<SpecialistResponseDto> findAllByRatingDesc(PageRequest page) {
        Slice<SpecialistEntity> slice = specialistRepository.findAllByRatingDesc(Pageable.ofSize(page.pageSize()).withPage(page.pageNumber()));
        return new PageResponse<>(
                mapper.toResponseDtoList(slice.getContent()),
                (countService.countAll() + page.pageSize() - 1) / page.pageSize()
        );
    }

    //@Cacheable(value = "specialists:filter", key = "#filter.cacheKey()", condition = "#filter.pageNumber() < 2")
    @Transactional(readOnly = true)
    @Override
    public PageResponse<SpecialistResponseDto> findAllByFilter(SpecialistFilter filter) {
        Slice<SpecialistEntity> slice = specialistRepository.findAll(
                PaginationUtils.generateSpecification(filter), PaginationUtils.generatePageable(filter)
        );
        return new PageResponse<>(
                mapper.toResponseDtoList(slice.getContent()),
                (countService.countByFilter(filter) + filter.pageSize() - 1) / filter.pageSize()

        );
    }

    //@Cacheable(value = "specialists:created:all", key = "#creatorId + ':' + #page.cacheKey()", condition = "#page.pageNumber() < 2")
    @Transactional(readOnly = true)
    @Override
    public PageResponse<SpecialistResponseDto> findAllByCreatorId(UUID creatorId, PageRequest page) {

        Specification<SpecialistEntity> specification = Specification
                .where(SpecialistSpecification.filterByCreatorId(creatorId));

        Slice<SpecialistEntity> slice = specialistRepository.findAll(
                specification, PaginationUtils.generatePageable(page)
        );

        return new PageResponse<>(
                mapper.toResponseDtoList(slice.getContent()),
                (countService.countByCreatorId(creatorId) + page.pageSize() - 1) / page.pageSize()
        );
    }

    //@Cacheable(value = "specialists:created:filter", key = "#creatorId + ':' + #filter.cacheKey()")
    @Transactional(readOnly = true)
    @Override
    public PageResponse<SpecialistResponseDto> findAllByCreatorIdAndFilter(UUID creatorId, ExtendedSpecialistFilter filter) {

        Specification<SpecialistEntity> specification = PaginationUtils.generateSpecification(filter)
                .and(SpecialistSpecification.filterByCreatorId(creatorId));

        Slice<SpecialistEntity> slice = specialistRepository.findAll(
                specification, PaginationUtils.generatePageable(filter)
        );

        return new PageResponse<>(
                mapper.toResponseDtoList(slice.getContent()),
                (countService.countByCreatorIdAndFilter(creatorId, filter) + filter.pageSize() - 1) / filter.pageSize()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<SpecialistResponseDto> findAllByFilterIn(ExtendedSpecialistFilter filter, List<UUID> ids) {
        Specification<SpecialistEntity> specification = PaginationUtils.generateSpecification(filter)
                .and(SpecialistSpecification.filterByIdIn(ids));

        Page<SpecialistEntity> page = specialistRepository.findAll(
                specification, PaginationUtils.generatePageable(filter)
        );

        return new PageResponse<>(mapper.toResponseDtoList(page.getContent()), page.getTotalPages());
    }

    @Override
    public SpecialistResponseDto toResponseDto(SpecialistEntity entity) {
        return mapper.toResponseDto(entity);
    }

    @Override
    public List<SpecialistResponseDto> toResponseDtoList(List<SpecialistEntity> entityList) {
        return mapper.toResponseDtoList(entityList);
    }
}