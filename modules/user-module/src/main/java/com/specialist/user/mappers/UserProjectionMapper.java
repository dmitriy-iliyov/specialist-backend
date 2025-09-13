package com.specialist.user.mappers;

import com.specialist.contracts.user.dto.PublicUserResponseDto;
import com.specialist.user.models.ShortUserProjection;
import com.specialist.user.repositories.AvatarStorage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AvatarStorage.class}
)
public interface UserProjectionMapper {
    @Mapping(target = "fullName", expression = "java(projection.getFullName())")
    @Mapping(target = "avatarUrl", source = "avatarUrl", qualifiedByName = "resolveAvatarUrl")
    PublicUserResponseDto toPublicDto(ShortUserProjection projection);

    List<PublicUserResponseDto> toPublicDtoList(List<ShortUserProjection> projections);
}
