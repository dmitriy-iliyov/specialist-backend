package com.specialist.profile.services;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.contracts.profile.SystemSpecialistProfileService;
import com.specialist.profile.exceptions.SpecialistNotFoundByIdException;
import com.specialist.profile.mappers.SpecialistProfileMapper;
import com.specialist.profile.models.ProfileFilter;
import com.specialist.profile.models.SpecialistProfileEntity;
import com.specialist.profile.models.dtos.PrivateSpecialistResponseDto;
import com.specialist.profile.models.dtos.PublicSpecialistResponseDto;
import com.specialist.profile.models.dtos.SpecialistCreateDto;
import com.specialist.profile.models.dtos.SpecialistUpdateDto;
import com.specialist.profile.models.enums.SpecialistStatus;
import com.specialist.profile.repositories.SpecialistProfileRepository;
import com.specialist.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialistProfileServiceImpl implements SpecialistProfileService,
                                                     ProfilePersistService<SpecialistCreateDto, PrivateSpecialistResponseDto>,
                                                     ProfileDeleteStrategy, SystemSpecialistProfileService {

    private final SpecialistProfileRepository repository;
    private final SpecialistProfileMapper mapper;

    @Transactional
    @Override
    public PrivateSpecialistResponseDto save(SpecialistCreateDto dto) {
        SpecialistProfileEntity entity = mapper.toEntity(dto);
        entity.setStatus(SpecialistStatus.UNAPPROVED);
        entity.setHasCard(false);
        return mapper.toPrivateDto(repository.save(entity));
    }

    @Transactional
    @Override
    public void approve(UUID id) {
        repository.updateStatusById(id, SpecialistStatus.APPROVED);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<PrivateSpecialistResponseDto> findAll(ProfileFilter filter) {
        PageRequest pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize());
        Page<SpecialistProfileEntity> entityPage = repository.findAll(pageable);
        return new PageResponse<>(
                mapper.toPrivateDtoList(entityPage.getContent()),
                entityPage.getTotalPages()
        );
    }

    @Transactional
    @Override
    public void setSpecialistCardId(UUID cardId) {
        repository.updateCardId(cardId);
    }

    @Override
    public ProfileType getType() {
        return ProfileType.SPECIALIST;
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public PrivateSpecialistResponseDto update(SpecialistUpdateDto dto) {
        SpecialistProfileEntity entity = repository.findById(dto.getId()).orElseThrow(SpecialistNotFoundByIdException::new);
        mapper.updateEntityFromDto(dto, entity);
        return mapper.toPrivateDto(repository.save(entity));
    }

    @Transactional(readOnly = true)
    @Override
    public PrivateSpecialistResponseDto findPrivateById(UUID id) {
        return mapper.toPrivateDto(repository.findById(id).orElseThrow(SpecialistNotFoundByIdException::new));
    }

    @Transactional(readOnly = true)
    @Override
    public PublicSpecialistResponseDto findPublicById(UUID id) {
        return mapper.toPublicDto(repository.findByIdAndStatus(id, SpecialistStatus.APPROVED)
                .orElseThrow(SpecialistNotFoundByIdException::new));
    }

    @Transactional
    @Override
    public void updateAvatarUrlById(UUID id, String avatarUrl) {
        repository.updateAvatarUrlById(id, avatarUrl);
    }
}
