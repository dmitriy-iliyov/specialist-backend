package com.aidcompass.core.contact.core.services;

import com.aidcompass.contracts.ContactType;
import com.aidcompass.core.contact.contact_type.models.ContactTypeEntity;
import com.aidcompass.core.contact.core.models.dto.system.SystemContactDto;
import com.aidcompass.core.contact.core.models.dto.system.SystemContactUpdateDto;

import java.util.List;
import java.util.UUID;

public interface SystemContactService {

    SystemContactDto save(SystemContactDto systemDto);

    void confirmContactById(Long contactId);

    boolean existsByTypeEntityAndContact(ContactTypeEntity typeEntity, String contact);

    SystemContactDto findById(Long contactId);

    SystemContactDto findByContact(String contact);

    List<SystemContactDto> findAllByOwnerId(UUID ownerId);

    long countByOwnerIdAndContactType(UUID ownerId, ContactType type);

    SystemContactDto update(SystemContactUpdateDto dto);
}
