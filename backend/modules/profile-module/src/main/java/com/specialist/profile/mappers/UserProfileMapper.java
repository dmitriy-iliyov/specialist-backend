package com.specialist.profile.mappers;


import com.specialist.profile.models.UserProfileEntity;
import com.specialist.profile.models.dtos.PrivateUserResponseDto;
import com.specialist.profile.models.dtos.PublicUserResponseDto;
import com.specialist.profile.models.dtos.UserCreateDto;
import com.specialist.profile.models.dtos.UserUpdateDto;
import com.specialist.utils.InstantToLocalDataTimeConverter;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {InstantToLocalDataTimeConverter.class, ProfilePictureUrlResolver.class}
)
public interface UserProfileMapper {

    UserProfileEntity toEntity(UserCreateDto dto);

    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    @Mapping(target = "avatarUrl", source = "avatarUrl", qualifiedByName = "resolvePictureUrl")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    PrivateUserResponseDto toPrivateDto(UserProfileEntity entity);

    List<PrivateUserResponseDto> toPrivateDtoList(List<UserProfileEntity> entityList);

    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    @Mapping(target = "avatarUrl", source = "avatarUrl", qualifiedByName = "resolvePictureUrl")
    PublicUserResponseDto toPublicDto(UserProfileEntity entity);

    List<PublicUserResponseDto> toPublicDtoList(List<UserProfileEntity> entityList);

    void updateEntityFromDto(UserUpdateDto dto, @MappingTarget UserProfileEntity entity);
}
