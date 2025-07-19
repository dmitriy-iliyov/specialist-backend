package com.aidcompass.specialistdirectory.domain.specialist;

import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistCreateDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistUpdateDto;
import com.aidcompass.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.aidcompass.specialistdirectory.domain.specialist.models.dtos.SpecialistResponseDto;
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
    SpecialistResponseDto toResponseDto(SpecialistEntity entity);

    List<SpecialistResponseDto> toResponseDtoList(List<SpecialistEntity> content);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "summaryRating", ignore = true)
    @Mapping(target = "totalRating", ignore = true)
    @Mapping(target = "reviewsCount", ignore = true)
    void updateEntityFromDto(SpecialistUpdateDto dto, @MappingTarget SpecialistEntity entity);
}