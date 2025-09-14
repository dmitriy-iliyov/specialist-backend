package com.specialist.user.mappers;

import com.specialist.contracts.user.dto.UnifiedProfileResponseDto;
import com.specialist.user.models.ShortProfileProjection;
import com.specialist.user.repositories.AvatarStorage;
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

    List<UnifiedProfileResponseDto> toPublicDtoList(List<ShortProfileProjection> projections);
}
