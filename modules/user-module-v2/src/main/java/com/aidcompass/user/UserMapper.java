package com.aidcompass.user;


import com.aidcompass.user.models.UserEntity;
import com.aidcompass.user.models.dto.UserCreateDto;
import com.aidcompass.user.models.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserEntity fromBaseDtoToEntity(UserDto dto);

    UserEntity toEntity(UserCreateDto dto);
}
