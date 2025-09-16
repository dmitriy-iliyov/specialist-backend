package com.specialist.user.mappers;

import com.specialist.contracts.specialistdirectory.dto.ManagedSpecialistResponseDto;
import com.specialist.user.models.SpecialistEntity;
import com.specialist.user.models.dtos.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpecialistMapper {

    SpecialistEntity toEntity(SpecialistCreateDto dto);

    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    @Mapping(target = "avatarUrl", source = "avatarUrl", qualifiedByName = "resolveAvatarUrl")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    PrivateSpecialistResponseDto toPrivateDto(SpecialistEntity entity);

    List<PrivateSpecialistResponseDto> toPrivateDtoList(List<SpecialistEntity> entityList);

    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    @Mapping(target = "avatarUrl", source = "avatarUrl", qualifiedByName = "resolveAvatarUrl")
    PublicSpecialistResponseDto toPublicDto(SpecialistEntity entity);

    List<PublicSpecialistResponseDto> toPublicDtoList(List<SpecialistEntity> entityList);

    void updateEntityFromDto(SpecialistUpdateDto dto, @MappingTarget SpecialistEntity entity);

    @Mapping(target = "card", source = "card")
    PrivateSpecialistAggregatedResponseDto aggregate(PrivateSpecialistResponseDto dto, ManagedSpecialistResponseDto card);

    @Mapping(target = "card", source = "card")
    PublicSpecialistAggregatedResponseDto aggregate(PublicSpecialistResponseDto dto, ManagedSpecialistResponseDto card);
}
