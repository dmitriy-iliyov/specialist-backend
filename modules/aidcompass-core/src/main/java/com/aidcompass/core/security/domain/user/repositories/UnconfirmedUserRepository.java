package com.aidcompass.core.security.domain.user.repositories;


import com.aidcompass.core.security.domain.user.models.UnconfirmedUserEntity;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnconfirmedUserRepository extends KeyValueRepository<UnconfirmedUserEntity, String> { }