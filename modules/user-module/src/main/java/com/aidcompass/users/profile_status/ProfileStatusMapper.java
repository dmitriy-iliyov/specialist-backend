package com.aidcompass.users.profile_status;

import com.aidcompass.users.profile_status.models.ProfileStatus;
import com.aidcompass.users.profile_status.models.ProfileStatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProfileStatusMapper {

    @Mapping(target = "profileStatus", source = "status")
    ProfileStatusEntity toEntity(ProfileStatus status);

    List<ProfileStatusEntity> toEntityList(List<ProfileStatus> statusList);
}