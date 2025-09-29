package com.specialist.specialistdirectory.domain.type.services;

import com.specialist.specialistdirectory.domain.type.models.dtos.FullTypeResponseDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.TranslateResponseDto;
import com.specialist.specialistdirectory.domain.type.models.dtos.TypeResponseDto;
import com.specialist.utils.pagination.PageRequest;
import com.specialist.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeAggregatorImpl implements TypeAggregator {

    private final TypeService service;
    private final TranslateService translateService;

    @Transactional(readOnly = true)
    @Override
    public PageResponse<FullTypeResponseDto> findAll(PageRequest page) {
        PageResponse<TypeResponseDto> typesPage = service.findAll(page);
        return new PageResponse<>(
                toFullDto(typesPage.data()),
                typesPage.totalPages()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<FullTypeResponseDto> findAllUnapproved(PageRequest page) {
        PageResponse<TypeResponseDto> typesPage = service.findAllUnapproved(page);
        return new PageResponse<>(
                toFullDto(typesPage.data()),
                typesPage.totalPages()
        );
    }

    private List<FullTypeResponseDto> toFullDto(List<TypeResponseDto> types) {
        Set<Long> ids = types.stream().map(TypeResponseDto::id).collect(Collectors.toSet());
        Map<Long, List<TranslateResponseDto>> translateMap = translateService.findAllByIdIn(ids);
        return types.stream()
                .map(type -> new FullTypeResponseDto(type, translateMap.get(type.id())))
                .toList();
    }
}
