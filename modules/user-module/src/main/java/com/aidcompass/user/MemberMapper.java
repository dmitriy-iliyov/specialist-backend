package com.aidcompass.user;


import com.aidcompass.user.models.MemberEntity;
import com.aidcompass.user.models.dto.MemberUpdateDto;
import com.aidcompass.user.models.dto.PrivateMemberResponseDto;
import com.aidcompass.user.models.dto.PublicMemberResponseDto;
import com.aidcompass.user.models.dto.MemberCreateDto;
import com.aidcompass.utils.InstantToLocalDataTimeConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {InstantToLocalDataTimeConverter.class})
public interface MemberMapper {

    @Mapping(target = "avatar", ignore = true)
    MemberEntity toEntity(MemberCreateDto dto);

    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    PrivateMemberResponseDto toPrivateDto(MemberEntity entity);

    List<PrivateMemberResponseDto> toPrivateDtoList(List<MemberEntity> entityList);

    @Mapping(target = "fullName", expression = "java(entity.getFullName())")
    PublicMemberResponseDto toPublicDto(MemberEntity entity);

    List<PublicMemberResponseDto> toPublicDtoList(List<MemberEntity> entityList);

    @Mapping(target = "avatar", ignore = true)
    void updateEntityFromDto(MemberUpdateDto dto, @MappingTarget MemberEntity entity);
}
