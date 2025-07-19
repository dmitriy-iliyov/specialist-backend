package com.aidcompass.specialistdirectory.domain.type;

import com.aidcompass.specialistdirectory.domain.type.models.TypeEntity;
import com.aidcompass.specialistdirectory.domain.type.models.dtos.ShortTypeResponseDto;
import com.aidcompass.specialistdirectory.domain.type.models.dtos.TypeCreateDto;
import com.aidcompass.specialistdirectory.domain.type.models.dtos.TypeResponseDto;
import com.aidcompass.specialistdirectory.domain.type.models.dtos.TypeUpdateDto;
import com.aidcompass.specialistdirectory.utils.converters.InstantToLocalDataTimeConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {InstantToLocalDataTimeConverter.class}
)
public interface TypeMapper {

    @Mapping(target = "title", expression = "java(dto.getTitle().toUpperCase())")
    TypeEntity toEntity(TypeCreateDto dto);

    @Mapping(target = "isApproved", source = "approved")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    TypeResponseDto toDto(TypeEntity entity);

    List<TypeResponseDto> toDtoList(List<TypeEntity> entityList);

    @Mapping(target = "title", expression = "java(dto.getTitle().toUpperCase())")
    void updateEntityFromDto(TypeUpdateDto dto, @MappingTarget TypeEntity entity);

    ShortTypeResponseDto toShortDto(TypeEntity typeEntity);
}