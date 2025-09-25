package com.specialist.profile.mappers;

import com.specialist.contracts.notification.SystemShortProfileResponseDto;
import com.specialist.contracts.profile.dto.UnifiedProfileResponseDto;
import com.specialist.profile.models.ShortProfileProjection;
import com.specialist.profile.repositories.AvatarStorage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AvatarStorage.class}
)
public interface ProfileProjectionMapper {
    @Mapping(target = "fullName", expression = "java(projection.getFullName())")
    @Mapping(target = "avatarUrl", source = "avatarUrl", qualifiedByName = "resolveAvatarUrl")
    UnifiedProfileResponseDto toPublicDto(ShortProfileProjection projection);

    @Mapping(target = "fullName", expression = "java(projection.getFullName())")
    SystemShortProfileResponseDto toSystemShortDto(ShortProfileProjection projection);

    List<UnifiedProfileResponseDto> toPublicDtoList(List<ShortProfileProjection> projections);
}
