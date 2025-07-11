package com.aidcompass.core.contact.core.mappers;

import com.aidcompass.core.contact.core.models.dto.system.SystemContactCreateDto;
import com.aidcompass.core.contact.core.models.dto.system.SystemContactDto;
import com.aidcompass.core.contact.core.models.entity.UnconfirmedContactEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UnconfirmedContactMapper {

    UnconfirmedContactEntity toEntity(SystemContactCreateDto dto);

    @Mapping(target = "primary", expression = "java(true)")
    @Mapping(target = "linkedToAccount", expression = "java(true)")
    SystemContactDto toSystemDto(UnconfirmedContactEntity entity);
}
