package com.specialist.user.services;

import com.specialist.contracts.user.ProfileType;
import com.specialist.contracts.user.SystemSpecialistProfileService;
import com.specialist.user.exceptions.SpecialistNotFoundByIdException;
import com.specialist.user.mappers.SpecialistMapper;
import com.specialist.user.models.SpecialistEntity;
import com.specialist.user.models.dtos.PrivateSpecialistResponseDto;
import com.specialist.user.models.dtos.PublicSpecialistResponseDto;
import com.specialist.user.models.dtos.SpecialistCreateDto;
import com.specialist.user.models.dtos.SpecialistUpdateDto;
import com.specialist.user.models.enums.SpecialistStatus;
import com.specialist.user.repositories.SpecialistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecialistServiceImpl implements SpecialistService,
                                              ProfilePersistService<SpecialistCreateDto, PrivateSpecialistResponseDto>,
                                              ProfileDeleteService, SystemSpecialistProfileService {

    private final SpecialistRepository repository;
    private final SpecialistMapper mapper;

    @Transactional
    @Override
    public PrivateSpecialistResponseDto save(SpecialistCreateDto dto) {
        SpecialistEntity entity = mapper.toEntity(dto);
        entity.setStatus(SpecialistStatus.UNAPPROVED);
        entity.setHasCard(false);
        return mapper.toPrivateDto(repository.save(entity));
    }

    @Transactional
    @Override
    public void approve(UUID id) {
        repository.updateStatusById(id, SpecialistStatus.APPROVED);
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
        SpecialistEntity entity = repository.findById(dto.getId()).orElseThrow(SpecialistNotFoundByIdException::new);
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
