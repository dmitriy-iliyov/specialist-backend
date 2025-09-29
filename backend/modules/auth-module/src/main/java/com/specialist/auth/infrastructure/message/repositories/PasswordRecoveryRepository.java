package com.specialist.auth.infrastructure.message.repositories;

import com.specialist.auth.infrastructure.message.models.PasswordRecoveryEntity;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRecoveryRepository extends KeyValueRepository<PasswordRecoveryEntity, String> {
}
