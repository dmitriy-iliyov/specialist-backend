package com.specialist.specialistdirectory.domain.type;

import com.specialist.specialistdirectory.domain.type.models.TranslateEntity;
import com.specialist.specialistdirectory.domain.type.models.dtos.*;
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

    @Mapping(target = "typeId", source = "type.accountId")
    TranslateResponseDto toDto(TranslateEntity entity);

    List<TranslateResponseDto> toDtoList(List<TranslateEntity> entityList);

    @Mapping(target = "type", ignore = true)
    void updateEntityFromDto(CompositeTranslateUpdateDto dto, @MappingTarget TranslateEntity entity);

    @Mapping(target = "type", ignore = true)
    void updateEntityFromDto(TranslateUpdateDto dto, @MappingTarget TranslateEntity entity);
}
