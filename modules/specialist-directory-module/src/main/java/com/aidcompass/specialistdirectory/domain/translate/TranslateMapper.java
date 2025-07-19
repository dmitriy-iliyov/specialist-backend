package com.aidcompass.specialistdirectory.domain.translate;

import com.aidcompass.specialistdirectory.domain.translate.models.TranslateEntity;
import com.aidcompass.specialistdirectory.domain.translate.models.dtos.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TranslateMapper {

    @Mapping(target = "type", ignore = true)
    TranslateEntity toEntity(CompositeTranslateCreateDto dto);

    @Mapping(target = "type", ignore = true)
    TranslateEntity toEntity(TranslateCreateDto dto);

    List<TranslateEntity> toEntityList(List<CompositeTranslateCreateDto> dtoList);

    @Mapping(target = "typeId", source = "type.id")
    TranslateResponseDto toDto(TranslateEntity entity);

    List<TranslateResponseDto> toDtoList(List<TranslateEntity> entityList);

    @Mapping(target = "type", ignore = true)
    void updateEntityFromDto(CompositeTranslateUpdateDto dto, @MappingTarget TranslateEntity entity);

    @Mapping(target = "type", ignore = true)
    void updateEntityFromDto(TranslateUpdateDto dto, @MappingTarget TranslateEntity entity);
}
