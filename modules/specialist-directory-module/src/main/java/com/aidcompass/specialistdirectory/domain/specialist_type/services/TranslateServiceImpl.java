package com.aidcompass.specialistdirectory.domain.specialist_type.services;

import com.aidcompass.specialistdirectory.domain.language.Language;
import com.aidcompass.specialistdirectory.domain.specialist_type.mappers.TranslateMapper;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.TranslateEntity;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.TypeEntity;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.ShortTypeResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TranslateCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TranslateResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TranslateUpdateDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.repositories.TranslateRepository;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases.TranslateService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TranslateServiceImpl implements TranslateService {

    private final TranslateRepository repository;
    private final TranslateMapper mapper;


    @Transactional
    @Override
    public List<TranslateResponseDto> saveAll(TypeEntity type, List<TranslateCreateDto> dtoList) {
        List<TranslateEntity> translates = repository.saveAll(mapper.toEntityList(dtoList));
        translates.forEach(entity -> entity.setType(type));
        return mapper.toDtoList(translates);
    }

    @Transactional
    @Override
    public List<TranslateResponseDto> updateAll(TypeEntity type, List<TranslateUpdateDto> dtoList) {
        List<TranslateEntity> entityList = repository.findAllByTypeId(type.getId());
        Map<Long, TranslateEntity> translatesMap = entityList.stream()
                .collect(Collectors.toMap(TranslateEntity::getId, Function.identity()));
        dtoList.forEach(dto -> mapper.updateEntityFromDto(dto, translatesMap.get(dto.id())));
        return mapper.toDtoList(repository.saveAll(entityList));
    }

    @Transactional(readOnly = true)
    @Override
    public List<TranslateResponseDto> findAllByTypeId(Long typeId) {
        return mapper.toDtoList(repository.findAllByTypeId(typeId));
    }

    @Cacheable(value = "specialists:types:approved:all", key = "'json:' + #language")
    @Transactional(readOnly = true)
    @Override
    public List<ShortTypeResponseDto> findAllApprovedAsJson(Language language) {
        return repository.findAllByLanguage(language).stream()
                .map(type -> new ShortTypeResponseDto(type.getType().getId(), type.getTranslate()))
                .toList();
    }

    @Cacheable(value = "specialists:types:approved:all", key = "'map:' + #language")
    @Transactional(readOnly = true)
    @Override
    public Map<Long, String> findAllApprovedAsMap(Language language) {
        return repository.findAllByLanguage(language).stream()
                .collect(Collectors.toMap(
                        entity -> entity.getType().getId(),
                        TranslateEntity::getTranslate)
                );
    }

    @Transactional(readOnly = true)
    @Override
    public Map<Long, List<TranslateResponseDto>> findAllByIdIn(Set<Long> ids) {
        List<TranslateResponseDto> dtoList = mapper.toDtoList(repository.findAllByTypeIdIn(ids));
        return dtoList.stream()
                .collect(Collectors.groupingBy(TranslateResponseDto::typeId));
    }
}
