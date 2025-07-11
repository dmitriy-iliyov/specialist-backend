package com.aidcompass.core.security.domain.user.services;

import com.aidcompass.core.security.domain.user.models.UnconfirmedUserEntity;
import com.aidcompass.core.security.domain.user.mapper.UserMapper;
import com.aidcompass.core.security.domain.user.models.dto.SystemUserDto;
import com.aidcompass.core.security.domain.user.models.dto.UserRegistrationDto;
import com.aidcompass.core.security.domain.user.repositories.UnconfirmedUserRepository;
import com.aidcompass.core.security.exceptions.not_found.UnconfirmedUserNotFoundByEmailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class UnconfirmedUserServiceImpl implements UnconfirmedUserService {

    private final UnconfirmedUserRepository unconfirmedUserRepository;
    private final UserMapper userMapper;


    @Override
    public void save(UUID id, UserRegistrationDto dto) {
        unconfirmedUserRepository.save(userMapper.toUnconfirmedEntity(id, dto));
        System.out.println("user in cache: " + dto);
    }

    @Override
    public boolean existsByEmail(String email) {
        return unconfirmedUserRepository.existsById(email);
    }

    @Override
    public SystemUserDto systemFindByEmail(String email, Long emailId) {
        UnconfirmedUserEntity unconfirmedUserEntity = unconfirmedUserRepository.findById(email).orElseThrow(
                UnconfirmedUserNotFoundByEmailException::new
        );
        return userMapper.toSystemDto(emailId, unconfirmedUserEntity);
    }

    @Override
    public SystemUserDto systemFindByEmail(String email) {
        UnconfirmedUserEntity unconfirmedUserEntity = unconfirmedUserRepository.findById(email).orElseThrow(
                UnconfirmedUserNotFoundByEmailException::new
        );
        return userMapper.toSystemDto(unconfirmedUserEntity);
    }

    @Override
    public void deleteByEmail(String email) {
        unconfirmedUserRepository.deleteById(email);
    }
}
