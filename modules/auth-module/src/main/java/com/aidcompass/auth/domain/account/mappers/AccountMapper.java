package com.aidcompass.auth.domain.account.mappers;

import com.aidcompass.auth.domain.account.models.AccountEntity;
import com.aidcompass.auth.domain.account.models.dtos.AccountResponseDto;
import com.aidcompass.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.aidcompass.auth.domain.authority.Authority;
import com.aidcompass.utils.InstantToLocalDataTimeConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {InstantToLocalDataTimeConverter.class})
public interface AccountMapper {

    @Mapping(target = "createdAt", source = "createdAt")
    ShortAccountResponseDto toShortResponseDto(AccountEntity save);

    @Mapping(target = "role", source = "role.role")
    @Mapping(target = "authorities", source = "paramAuthorities")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    AccountResponseDto toResponseDto(List<Authority> paramAuthorities, AccountEntity entity);

    List<AccountResponseDto> toResponseDtoList(List<AccountEntity> entityList);
}
