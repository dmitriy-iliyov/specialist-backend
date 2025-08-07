package com.specialist.auth.infrastructure.message.repositories;

import com.specialist.auth.infrastructure.message.models.ConfirmationEntity;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationRepository extends KeyValueRepository<ConfirmationEntity, String> {
}
