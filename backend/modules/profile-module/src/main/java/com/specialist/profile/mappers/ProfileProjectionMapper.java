package com.specialist.profile.mappers;

import com.specialist.contracts.notification.SystemShortProfileResponseDto;
import com.specialist.contracts.profile.dto.UnifiedProfileResponseDto;
import com.specialist.picture.PictureStorage;
import com.specialist.profile.models.ShortProfileProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ProfileProjectionMapper {

    @Qualifier("profilePictureStorage")
    protected PictureStorage pictureStorage;

    @Named("resolvePictureUrl")
    protected String resolvePictureUrl(String avatarUrl) {
        return pictureStorage.resolvePictureUrl(avatarUrl);
    }

    @Mapping(target = "fullName", expression = "java(projection.getFullName())")
    @Mapping(target = "avatarUrl", source = "avatarUrl", qualifiedByName = "resolvePictureUrl")
    public abstract UnifiedProfileResponseDto toPublicDto(ShortProfileProjection projection);

    @Mapping(target = "fullName", expression = "java(projection.getFullName())")
    public abstract SystemShortProfileResponseDto toSystemShortDto(ShortProfileProjection projection);

    public abstract List<UnifiedProfileResponseDto> toPublicDtoList(List<ShortProfileProjection> projections);
}
