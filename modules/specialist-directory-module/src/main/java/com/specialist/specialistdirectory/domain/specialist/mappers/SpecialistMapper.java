package com.specialist.specialistdirectory.domain.specialist.mappers;

import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.BookmarkSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpecialistMapper {

    @Mapping(target = "type", ignore = true)
    SpecialistEntity toEntity(SpecialistCreateDto dto);

    @Mapping(target = "typeTitle", source = "type.title")
    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    @Mapping(target = "address", expression = "java(entity.getAddress())")
    SpecialistResponseDto toResponseDto(SpecialistEntity entity);

    @Mapping(target = "typeTitle", source = "type.title")
    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    @Mapping(target = "address", expression = "java(entity.getAddress())")
    BookmarkSpecialistResponseDto toBookmarkResponseDto(SpecialistEntity entity);

    List<SpecialistResponseDto> toResponseDtoList(List<SpecialistEntity> content);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "summaryRating", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "reviewsCount", ignore = true)
    void updateEntityFromDto(SpecialistUpdateDto dto, @MappingTarget SpecialistEntity entity);
}