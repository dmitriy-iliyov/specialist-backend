package com.aidcompass.specialistdirectory.domain.specialist_type.mappers;

import com.aidcompass.specialistdirectory.domain.specialist_type.models.TranslateEntity;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TranslateCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TranslateResponseDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TranslateUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TranslateMapper {

    @Mapping(target = "type", ignore = true)
    TranslateEntity toEntity(TranslateCreateDto dto);

    List<TranslateEntity> toEntityList(List<TranslateCreateDto> dtoList);

    @Mapping(target = "typeId", source = "type.id")
    TranslateResponseDto toDto(TranslateEntity entity);

    List<TranslateResponseDto> toDtoList(List<TranslateEntity> entityList);

    @Mapping(target = "type", ignore = true)
    void updateEntityFromDto(TranslateUpdateDto dto, @MappingTarget TranslateEntity entity);
}
