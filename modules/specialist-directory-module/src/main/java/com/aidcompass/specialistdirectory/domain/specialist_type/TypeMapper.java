package com.aidcompass.specialistdirectory.domain.specialist_type;

import com.aidcompass.specialistdirectory.domain.specialist_type.models.TypeEntity;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.ShortTypeDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeDto;
import com.aidcompass.specialistdirectory.domain.specialist_type.models.dtos.TypeUpdateDto;
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
    TypeDto toDto(TypeEntity entity);

    List<TypeDto> toDtoList(List<TypeEntity> entityList);

    @Mapping(target = "title", expression = "java(dto.getTitle().toUpperCase())")
    @Mapping(target = "approved", source = "approved")
    void updateEntityFromDto(TypeUpdateDto dto, @MappingTarget TypeEntity entity);

    ShortTypeDto toShortDto(TypeEntity typeEntity);
}