package com.aidcompass.core.security.domain.user.services;

import com.aidcompass.core.security.domain.user.models.dto.SystemUserUpdateDto;
import com.aidcompass.core.security.domain.user.models.dto.UserResponseDto;
import com.aidcompass.core.security.domain.authority.models.Authority;
import com.aidcompass.core.security.domain.user.models.MemberUserDetails;
import com.aidcompass.core.security.domain.user.models.dto.SystemUserDto;
import com.aidcompass.core.security.domain.user.models.dto.UserUpdateDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserService {

    void save(SystemUserDto systemUserDto);

    boolean existsById(UUID id);

    boolean existsByEmail(String email);

    UserResponseDto update(SystemUserUpdateDto systemUserUpdateDto);

    void updatePasswordByEmail(String email, String password);

    void confirmByEmail(String email, Long emailId);

    SystemUserDto systemFindByEmail(String mail);

    MemberUserDetails changeAuthorityById(UUID id, Authority authority);

    SystemUserDto systemFindById(UUID id);

    List<SystemUserDto> findAllByIdIn(Set<UUID> ids);

    void deleteById(UUID id);

    void deleteByPassword(UUID id, String password);

    SystemUserUpdateDto mapToUpdateDto(UserUpdateDto userUpdateDto);

    UserResponseDto findById(UUID id);
}
