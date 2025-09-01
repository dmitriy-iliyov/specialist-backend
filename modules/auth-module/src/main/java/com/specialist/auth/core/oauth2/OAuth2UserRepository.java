package com.specialist.auth.core.oauth2;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuth2UserRepository extends KeyValueRepository<OAuth2UserEntity, String> { }
