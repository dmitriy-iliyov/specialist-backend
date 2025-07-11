package com.aidcompass.users.jurist.mapper;


import com.aidcompass.users.detail.mapper.DetailMapper;
import com.aidcompass.users.jurist.models.JuristEntity;
import com.aidcompass.users.jurist.models.dto.FullPrivateJuristResponseDto;
import com.aidcompass.users.jurist.models.dto.FullPublicJuristResponseDto;
import com.aidcompass.users.jurist.specialization.models.JuristSpecialization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {JuristMapper.class, DetailMapper.class}
)
public interface FullJuristMapper {

    @Mapping(target = "jurist", expression = "java(juristMapper.toPrivateDto(entity))")
    @Mapping(target = "detail", source = "entity.detailEntity")
    FullPrivateJuristResponseDto toFullPrivateDto(JuristEntity entity);

    @Mapping(target = "jurist", expression = "java(juristMapper.toPrivateDto(paramSpecializations, entity))")
    @Mapping(target = "detail", source = "entity.detailEntity")
    FullPrivateJuristResponseDto toFullPrivateDto(List<JuristSpecialization> paramSpecializations, JuristEntity entity);

    @Mapping(target = "jurist", source = "entity")
    @Mapping(target = "detail", source = "entity.detailEntity")
    FullPublicJuristResponseDto toFullPublicDto(JuristEntity entity);
}
