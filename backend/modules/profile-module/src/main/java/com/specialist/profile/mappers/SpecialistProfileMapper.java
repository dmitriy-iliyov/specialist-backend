package com.specialist.profile.mappers;

import com.specialist.contracts.specialistdirectory.dto.ManagedSpecialistResponseDto;
import com.specialist.profile.models.SpecialistProfileEntity;
import com.specialist.profile.models.dtos.*;
import com.specialist.profile.repositories.AvatarStorage;
import com.specialist.utils.InstantToLocalDataTimeConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {InstantToLocalDataTimeConverter.class, AvatarStorage.class}
)
public interface SpecialistProfileMapper {

    SpecialistProfileEntity toEntity(SpecialistCreateDto dto);

    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    @Mapping(target = "avatarUrl", source = "avatarUrl", qualifiedByName = "resolveAvatarUrl")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    PrivateSpecialistResponseDto toPrivateDto(SpecialistProfileEntity entity);

    List<PrivateSpecialistResponseDto> toPrivateDtoList(List<SpecialistProfileEntity> entityList);

    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    @Mapping(target = "avatarUrl", source = "avatarUrl", qualifiedByName = "resolveAvatarUrl")
    PublicSpecialistResponseDto toPublicDto(SpecialistProfileEntity entity);

    List<PublicSpecialistResponseDto> toPublicDtoList(List<SpecialistProfileEntity> entityList);

    void updateEntityFromDto(SpecialistUpdateDto dto, @MappingTarget SpecialistProfileEntity entity);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "type", source = "dto.type")
    @Mapping(target = "fullName", source = "dto.fullName")
    @Mapping(target = "email", source = "dto.email")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "aboutMe", source = "dto.aboutMe")
    @Mapping(target = "hasCard", source = "dto.hasCard")
    @Mapping(target = "createdAt", source = "dto.createdAt")
    @Mapping(target = "updatedAt", source = "dto.updatedAt")
    @Mapping(target = "card", source = "card")
    PrivateSpecialistAggregatedResponseDto aggregate(PrivateSpecialistResponseDto dto, ManagedSpecialistResponseDto card);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "type", source = "dto.type")
    @Mapping(target = "fullName", source = "dto.fullName")
    @Mapping(target = "card", source = "card")
    PublicSpecialistAggregatedResponseDto aggregate(PublicSpecialistResponseDto dto, ManagedSpecialistResponseDto card);

    default List<PrivateSpecialistAggregatedResponseDto> aggregate(List<PrivateSpecialistResponseDto> dtoList,
                                                           List<ManagedSpecialistResponseDto> managedDtoList) {
        List<PrivateSpecialistAggregatedResponseDto> aggregatedDtoList = new ArrayList<>();
        Map<UUID, ManagedSpecialistResponseDto> managedDtoMap = managedDtoList.stream()
                .collect(Collectors.toMap(ManagedSpecialistResponseDto::getOwnerId, Function.identity()));
        for (PrivateSpecialistResponseDto dto : dtoList) {
            PrivateSpecialistAggregatedResponseDto aggregatedDto = aggregate(dto, managedDtoMap.get(dto.getId()));
            aggregatedDtoList.add(aggregatedDto);
        }
        return aggregatedDtoList;
    }
}
