package com.aidcompass.core.security.domain.token;

import com.aidcompass.core.security.domain.token.models.TokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeactivateTokenRepository extends CrudRepository<TokenEntity, Long> {

    boolean existsById(UUID id);

}
