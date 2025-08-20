package com.specialist.specialistdirectory.domain.type.services;

import com.specialist.specialistdirectory.domain.language.Language;
import com.specialist.specialistdirectory.domain.type.TranslateMapper;
import com.specialist.specialistdirectory.domain.type.TranslateRepository;
import com.specialist.specialistdirectory.domain.type.models.TranslateEntity;
import com.specialist.specialistdirectory.domain.type.models.TypeEntity;
import com.specialist.specialistdirectory.domain.type.models.dtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
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
    public TranslateResponseDto save(TypeEntity type, TranslateCreateDto dto) {
        TranslateEntity entity = mapper.toEntity(dto);
        entity.setType(type);
        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    @Override
    public List<TranslateResponseDto> saveAll(TypeEntity type, List<CompositeTranslateCreateDto> dtoList) {
        List<TranslateEntity> translates = mapper.toEntityList(dtoList);
        translates.forEach(entity -> entity.setType(type));
        return mapper.toDtoList(repository.saveAll(translates));
    }

    @Transactional
    @Override
    public TranslateResponseDto update(TranslateUpdateDto dto) {
        TranslateEntity entity = repository.findByTypeId(dto.getTypeId()).orElseThrow();
        mapper.updateEntityFromDto(dto, entity);
        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    @Override
    public List<TranslateResponseDto> updateAll(TypeEntity type, List<CompositeTranslateUpdateDto> dtoList) {
        List<TranslateEntity> entityList = repository.findAllByTypeId(type.getId());
        Map<Long, TranslateEntity> translatesMap = entityList.stream()
                .collect(Collectors.toMap(TranslateEntity::getId, Function.identity()));
        dtoList.forEach(dto -> mapper.updateEntityFromDto(dto, translatesMap.get(dto.getId())));
        return mapper.toDtoList(repository.saveAll(entityList));
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

    @CacheEvict(value = "specialists:types:approved:all", allEntries = true)
    @Transactional
    @Override
    public void deleteById(Long id, Long typeId) {
        repository.deleteById(id);
    }
}
