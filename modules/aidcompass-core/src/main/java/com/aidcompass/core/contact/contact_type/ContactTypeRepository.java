package com.aidcompass.core.contact.contact_type;

import com.aidcompass.contracts.ContactType;
import com.aidcompass.core.contact.contact_type.models.ContactTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactTypeRepository extends JpaRepository<ContactTypeEntity, Integer> {
    Optional<ContactTypeEntity> findByType(ContactType type);
}
