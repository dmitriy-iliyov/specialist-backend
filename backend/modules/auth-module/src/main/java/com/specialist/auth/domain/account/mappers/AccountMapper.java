package com.specialist.auth.domain.account.mappers;

import com.specialist.auth.domain.account.models.AccountEntity;
import com.specialist.auth.domain.account.models.dtos.AccountResponseDto;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.utils.InstantToLocalDataTimeConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {InstantToLocalDataTimeConverter.class})
public interface AccountMapper {

    @Mapping(target = "createdAt", source = "createdAt")
    ShortAccountResponseDto toShortResponseDto(AccountEntity save);

    @Mapping(target = "role", source = "entity.role.role")
    @Mapping(target = "authorities", source = "paramAuthorities")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "updatedAt", source = "entity.updatedAt")
    AccountResponseDto toResponseDto(List<Authority> paramAuthorities, AccountEntity entity);
}
