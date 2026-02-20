package com.specialist.auth.domain.service_account;

import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.domain.service_account.models.ServiceAccountEntity;
import com.specialist.auth.domain.service_account.models.ServiceAccountResponseDto;
import com.specialist.utils.InstantToLocalDataTimeConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {InstantToLocalDataTimeConverter.class})
public interface ServiceAccountMapper {

    @Mapping(target = "role", source = "entity.role.role")
    @Mapping(target = "authorities", source = "paramAuthorities")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    ServiceAccountResponseDto toResponseDto(List<Authority> paramAuthorities, ServiceAccountEntity entity);
}
