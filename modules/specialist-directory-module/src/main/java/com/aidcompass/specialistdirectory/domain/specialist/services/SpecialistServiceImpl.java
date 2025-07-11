package com.aidcompass.specialistdirectory.domain.specialist.services;

import com.aidcompass.specialistdirectory.domain.specialist.SpecialistMapper;
import com.aidcompass.specialistdirectory.domain.specialist.SpecialistRepository;
import com.aidcompass.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.TypeService;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.TypeConstants;
import com.aidcompass.specialistdirectory.exceptions.SpecialistEntityNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistRepository specialistRepository;
    private final SpecialistMapper mapper;
    private final TypeService typeService;


    //@Cacheable
    @Transactional
    @Override
    public SpecialistResponseDto save(SpecialistCreateDto dto) {
        SpecialistEntity entity = mapper.toEntity(dto);
        Long typeId = dto.getTypeId();
        if (typeId.equals(TypeConstants.OTHER_TYPE_ID)) {
            saveSuggestedType(entity, dto.getAnotherType());
        }
        entity.setType(typeService.getReferenceById(typeId));
        entity.setTotalRating(0.0);
        entity.setReviewsCount(0);
        entity = specialistRepository.save(entity);
        return mapper.toResponseDto(entity);
    }

    @Transactional
    @Override
    public SpecialistResponseDto update(SpecialistUpdateDto dto) {
        SpecialistEntity entity = specialistRepository.findWithTypeById(dto.getId()).orElseThrow(SpecialistEntityNotFoundByIdException::new);
        // compare
        Long inputTypeId = dto.getTypeId();
        Long existedTypeId = entity.getType().getId();
        if (!existedTypeId.equals(inputTypeId)) {
            if (!existedTypeId.equals(TypeConstants.OTHER_TYPE_ID) && inputTypeId.equals(TypeConstants.OTHER_TYPE_ID)) {
                saveSuggestedType(entity, dto.getAnotherType());
            } else if (existedTypeId.equals(TypeConstants.OTHER_TYPE_ID)) {
                entity.setSuggestedTypeId(null);
            }
            entity.setType(typeService.getReferenceById(inputTypeId));
        } else if (existedTypeId.equals(TypeConstants.OTHER_TYPE_ID)) {
            TypeDto typeDto = typeService.findSuggestedById(entity.getSuggestedTypeId());
            if (!typeDto.title().equalsIgnoreCase(dto.getAnotherType())) {
                saveSuggestedType(entity, dto.getAnotherType());
            }
        }
        mapper.updateEntityFromDto(dto, entity);
        entity = specialistRepository.save(entity);
        return mapper.toResponseDto(entity);
    }

    private void saveSuggestedType(SpecialistEntity entity, String suggestedType) {
        Long id = typeService.saveSuggested(new TypeCreateDto(suggestedType.strip()));
        entity.setSuggestedTypeId(id);
    }

    //@Transactional(readOnly = true)
    @Override
    public UUID getCreatorIdById(UUID id) {
        return specialistRepository.findCreatorIdById(id).orElseThrow(SpecialistEntityNotFoundByIdException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public SpecialistResponseDto findById(UUID userId, UUID id) {
        SpecialistEntity entity = specialistRepository.findWithTypeById(id).orElseThrow(
                SpecialistEntityNotFoundByIdException::new
        );
        SpecialistResponseDto dto = mapper.toResponseDto(entity);
        if (entity.getSuggestedTypeId() != null) {
            if (!entity.getCreatorId().equals(userId)) {
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
                SpecialistEntityNotFoundByIdException::new
        );
        SpecialistResponseDto dto = mapper.toResponseDto(entity);
        if (entity.getSuggestedTypeId() != null) {
            dto.setAnotherType(typeService.findSuggestedById(entity.getSuggestedTypeId()).title());
        }
        return dto;
    }

    @Transactional
    @Override
    public void updateAllByTypeIdPair(Long oldTypeId, Long newTypeId) {
        specialistRepository.updateAllByTypeTitle(oldTypeId, newTypeId);
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        specialistRepository.deleteById(id);
    }
}