package com.specialist.profile.mappers;


import com.specialist.picture.PictureStorage;
import com.specialist.profile.models.UserProfileEntity;
import com.specialist.profile.models.dtos.PrivateUserResponseDto;
import com.specialist.profile.models.dtos.PublicUserResponseDto;
import com.specialist.profile.models.dtos.UserCreateDto;
import com.specialist.profile.models.dtos.UserUpdateDto;
import com.specialist.utils.InstantToLocalDataTimeConverter;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {InstantToLocalDataTimeConverter.class}
)
public abstract class UserProfileMapper {

    @Qualifier("profilePictureStorage")
    protected PictureStorage pictureStorage;

    @Named("resolvePictureUrl")
    protected String resolvePictureUrl(String avatarUrl) {
        return pictureStorage.resolvePictureUrl(avatarUrl);
    }

    public abstract UserProfileEntity toEntity(UserCreateDto dto);

    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    @Mapping(target = "avatarUrl", source = "avatarUrl", qualifiedByName = "resolvePictureUrl")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    public abstract PrivateUserResponseDto toPrivateDto(UserProfileEntity entity);

    public abstract List<PrivateUserResponseDto> toPrivateDtoList(List<UserProfileEntity> entityList);

    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    @Mapping(target = "avatarUrl", source = "avatarUrl", qualifiedByName = "resolvePictureUrl")
    public abstract PublicUserResponseDto toPublicDto(UserProfileEntity entity);

    public abstract List<PublicUserResponseDto> toPublicDtoList(List<UserProfileEntity> entityList);

    public abstract void updateEntityFromDto(UserUpdateDto dto, @MappingTarget UserProfileEntity entity);
}
