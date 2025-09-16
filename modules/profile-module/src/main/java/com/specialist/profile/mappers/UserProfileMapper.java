package com.specialist.profile.mappers;


import com.specialist.profile.models.UserProfileEntity;
import com.specialist.profile.models.dtos.PrivateUserResponseDto;
import com.specialist.profile.models.dtos.PublicUserResponseDto;
import com.specialist.profile.models.dtos.UserCreateDto;
import com.specialist.profile.models.dtos.UserUpdateDto;
import com.specialist.profile.repositories.AvatarStorage;
import com.specialist.utils.InstantToLocalDataTimeConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {InstantToLocalDataTimeConverter.class, AvatarStorage.class}
)
public interface UserProfileMapper {

    UserProfileEntity toEntity(UserCreateDto dto);

    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    @Mapping(target = "avatarUrl", source = "avatarUrl", qualifiedByName = "resolveAvatarUrl")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    PrivateUserResponseDto toPrivateDto(UserProfileEntity entity);

    List<PrivateUserResponseDto> toPrivateDtoList(List<UserProfileEntity> entityList);

    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    @Mapping(target = "avatarUrl", source = "avatarUrl", qualifiedByName = "resolveAvatarUrl")
    PublicUserResponseDto toPublicDto(UserProfileEntity entity);

    List<PublicUserResponseDto> toPublicDtoList(List<UserProfileEntity> entityList);

    void updateEntityFromDto(UserUpdateDto dto, @MappingTarget UserProfileEntity entity);
}
