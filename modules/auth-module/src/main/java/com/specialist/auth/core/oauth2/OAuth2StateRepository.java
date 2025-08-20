package com.specialist.auth.core.oauth2;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuth2StateRepository extends KeyValueRepository<OAuth2StateEntity, String> { }
