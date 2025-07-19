package com.aidcompass.specialistdirectory.domain.type.services;

import com.aidcompass.specialistdirectory.domain.type.models.dtos.*;
import com.aidcompass.specialistdirectory.domain.translate.models.dtos.TranslateResponseDto;
import com.aidcompass.specialistdirectory.domain.type.services.interfaces.TypeService;
import com.aidcompass.specialistdirectory.domain.translate.services.interfaces.TranslateService;
import com.aidcompass.specialistdirectory.domain.type.services.interfaces.TypeOrchestrator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeOrchestratorImpl implements TypeOrchestrator {

    private final TypeService service;
    private final TranslateService translateService;
    private final Validator validator;


    @Transactional
    @Override
    public FullTypeResponseDto save(UUID creatorId, FullTypeCreateDto dto) {
        dto.type().setCreatorId(creatorId);
        TypeResponseDto type = service.save(dto.type());
        List<TranslateResponseDto> translates = translateService.saveAll(
                service.getReferenceById(type.id()), dto.translates()
        );
        return new FullTypeResponseDto(type, translates);
    }

    @Transactional
    @Override
    public FullTypeResponseDto update(Long typeId, FullTypeUpdateDto dto) {
        dto.type().setId(typeId);
        dto.translates().forEach(translate -> translate.setTypeId(typeId));
        TypeUpdateDto typeDto = dto.type();
        Set<ConstraintViolation<TypeUpdateDto>> bindingErrors = validator.validate(typeDto);
        if (!bindingErrors.isEmpty()) {
            throw new ConstraintViolationException(bindingErrors);
        }
        return new FullTypeResponseDto(
                service.update(typeDto),
                translateService.updateAll(service.getReferenceById(typeId), dto.translates())
        );
    }

    @Override
    public void deleteById(Long id) {
        service.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FullTypeResponseDto> findAll() {
        List<TypeResponseDto> types = service.findAll();
        return toFullDto(types);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FullTypeResponseDto> findAllUnapproved() {
        List<TypeResponseDto> types = service.findAllUnapproved();
        return toFullDto(types);
    }

    private List<FullTypeResponseDto> toFullDto(List<TypeResponseDto> types) {
        Set<Long> ids = types.stream().map(TypeResponseDto::id).collect(Collectors.toSet());
        Map<Long, List<TranslateResponseDto>> translateMap = translateService.findAllByIdIn(ids);
        return types.stream()
                .map(type -> new FullTypeResponseDto(type, translateMap.get(type.id())))
                .toList();
    }
}
