package com.aidcompass.specialistdirectory.domain.specialist_type.services;

import com.aidcompass.specialistdirectory.domain.language.Language;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.TypeEntity;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.TypeTranslateEntity;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.ShortTypeDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.repositories.TypeTranslateRepository;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases.TypeCacheService;
import com.aidcompass.specialistdirectory.domain.specialist_type.services.interfases.TypeTranslateService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeTranslateServiceImpl implements TypeTranslateService {

    private final TypeTranslateRepository repository;


    @Cacheable(value = "specialists:types:approved:all", key = "'json:' + #language")
    @Transactional(readOnly = true)
    @Override
    public List<ShortTypeDto> findAllApprovedAsJson(Language language) {
        return repository.findAllByLanguage(language).stream()
                .map(type -> new ShortTypeDto(type.getType().getId(), type.getTranslate()))
                .toList();
    }

    @Cacheable(value = "specialists:types:approved:all", key = "'map:' + #language")
    @Transactional(readOnly = true)
    @Override
    public Map<Long, String> findAllApprovedAsMap(Language language) {
        return repository.findAllByLanguage(language).stream()
                .collect(Collectors.toMap(
                        entity -> entity.getType().getId(),
                        TypeTranslateEntity::getTranslate)
                );
    }
}
