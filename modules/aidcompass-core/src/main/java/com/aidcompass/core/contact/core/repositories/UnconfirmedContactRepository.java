package com.aidcompass.core.contact.core.repositories;

import com.aidcompass.core.contact.core.models.entity.UnconfirmedContactEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnconfirmedContactRepository extends CrudRepository<UnconfirmedContactEntity, String> {
}
