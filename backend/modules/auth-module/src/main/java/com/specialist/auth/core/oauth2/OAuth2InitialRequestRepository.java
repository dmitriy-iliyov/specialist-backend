package com.specialist.auth.core.oauth2;

import com.specialist.auth.core.oauth2.models.OAuth2InitialRequestEntity;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuth2InitialRequestRepository extends KeyValueRepository<OAuth2InitialRequestEntity, String> { }
