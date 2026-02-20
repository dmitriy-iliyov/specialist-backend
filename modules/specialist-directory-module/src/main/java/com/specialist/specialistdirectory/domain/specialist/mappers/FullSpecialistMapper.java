package com.specialist.specialistdirectory.domain.specialist.mappers;

import com.specialist.specialistdirectory.domain.specialist.models.SpecialistEntity;
import com.specialist.specialistdirectory.domain.specialist.models.SpecialistInfoEntity;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.FullSpecialistResponseDto;
import com.specialist.specialistdirectory.domain.specialist.models.dtos.SpecialistInfoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FullSpecialistMapper {
    SpecialistInfoResponseDto toInfoResponseDto(SpecialistInfoEntity entity);

    @Mapping(target = "typeTitle", source = "type.title")
    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    @Mapping(target = "address", expression = "java(entity.getAddress())")
    FullSpecialistResponseDto toResponseDto(SpecialistEntity entity);

    List<FullSpecialistResponseDto> toResponseDtoList(List<SpecialistEntity> entityList);
}
