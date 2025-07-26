package com.aidcompass.user;


import com.aidcompass.contracts.user.PublicUserResponseDto;
import com.aidcompass.user.models.UserEntity;
import com.aidcompass.user.models.dto.PrivateUserResponseDto;
import com.aidcompass.user.models.dto.UserUpdateDto;
import com.aidcompass.user.models.dto.UserCreateDto;
import com.aidcompass.user.repositories.AvatarStorage;
import com.aidcompass.utils.InstantToLocalDataTimeConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {InstantToLocalDataTimeConverter.class, AvatarStorage.class}
)
public interface UserMapper {

    UserEntity toEntity(UserCreateDto dto);

    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    @Mapping(target = "avatarUrl", source = "avatarUrl", qualifiedByName = "resolveAvatarUrl")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    PrivateUserResponseDto toPrivateDto(UserEntity entity);

    List<PrivateUserResponseDto> toPrivateDtoList(List<UserEntity> entityList);

    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    @Mapping(target = "avatarUrl", source = "avatarUrl", qualifiedByName = "resolveAvatarUrl")
    PublicUserResponseDto toPublicDto(UserEntity entity);

    List<PublicUserResponseDto> toPublicDtoList(List<UserEntity> entityList);

    void updateEntityFromDto(UserUpdateDto dto, @MappingTarget UserEntity entity);
}
