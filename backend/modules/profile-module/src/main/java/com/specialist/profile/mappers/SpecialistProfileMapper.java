package com.specialist.profile.mappers;

import com.specialist.contracts.specialistdirectory.dto.ExternalManagedSpecialistResponseDto;
import com.specialist.picture.PictureStorage;
import com.specialist.profile.models.SpecialistProfileEntity;
import com.specialist.profile.models.dtos.*;
import com.specialist.utils.InstantToLocalDataTimeConverter;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {InstantToLocalDataTimeConverter.class}
)
public abstract class SpecialistProfileMapper {

    @Qualifier("profilePictureStorage")
    protected PictureStorage pictureStorage;

    @Named("resolvePictureUrl")
    protected String resolvePictureUrl(String avatarUrl) {
        return pictureStorage.resolvePictureUrl(avatarUrl);
    }

    public abstract SpecialistProfileEntity toEntity(SpecialistCreateDto dto);

    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    @Mapping(target = "avatarUrl", source = "avatarUrl", qualifiedByName = "resolvePictureUrl")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    public abstract PrivateSpecialistResponseDto toPrivateDto(SpecialistProfileEntity entity);

    public abstract List<PrivateSpecialistResponseDto> toPrivateDtoList(List<SpecialistProfileEntity> entityList);

    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    @Mapping(target = "avatarUrl", source = "avatarUrl", qualifiedByName = "resolvePictureUrl")
    public abstract PublicSpecialistResponseDto toPublicDto(SpecialistProfileEntity entity);

    public abstract List<PublicSpecialistResponseDto> toPublicDtoList(List<SpecialistProfileEntity> entityList);

    public abstract void updateEntityFromDto(SpecialistUpdateDto dto, @MappingTarget SpecialistProfileEntity entity);

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
    public abstract PrivateSpecialistAggregatedResponseDto aggregate(PrivateSpecialistResponseDto dto, ExternalManagedSpecialistResponseDto card);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "type", source = "dto.type")
    @Mapping(target = "fullName", source = "dto.fullName")
    @Mapping(target = "card", source = "card")
    public abstract PublicSpecialistAggregatedResponseDto aggregate(PublicSpecialistResponseDto dto, ExternalManagedSpecialistResponseDto card);

    public List<PrivateSpecialistAggregatedResponseDto> aggregate(List<PrivateSpecialistResponseDto> dtoList,
                                                                  List<ExternalManagedSpecialistResponseDto> managedDtoList) {
        List<PrivateSpecialistAggregatedResponseDto> aggregatedDtoList = new ArrayList<>();
        Map<UUID, ExternalManagedSpecialistResponseDto> managedDtoMap = managedDtoList.stream()
                .collect(Collectors.toMap(ExternalManagedSpecialistResponseDto::getOwnerId, Function.identity()));
        for (PrivateSpecialistResponseDto dto : dtoList) {
            PrivateSpecialistAggregatedResponseDto aggregatedDto = aggregate(dto, managedDtoMap.get(dto.getId()));
            aggregatedDtoList.add(aggregatedDto);
        }
        return aggregatedDtoList;
    }
}

