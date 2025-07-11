package com.aidcompass.users.detail.mapper;


import com.aidcompass.users.detail.models.DetailDto;
import com.aidcompass.users.detail.models.DetailEntity;
import com.aidcompass.users.detail.models.PrivateDetailResponseDto;
import com.aidcompass.users.detail.models.PublicDetailResponseDto;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface DetailMapper {

    @Named("toPrivateDetailDto")
    PrivateDetailResponseDto toPrivateDetailDto(DetailEntity entity);

    @Named("toPublicDetailDto")
    PublicDetailResponseDto toPublicDetailDto(DetailEntity entity);

    void updateEntityFromDto(DetailDto dto, @MappingTarget DetailEntity entity);
}
