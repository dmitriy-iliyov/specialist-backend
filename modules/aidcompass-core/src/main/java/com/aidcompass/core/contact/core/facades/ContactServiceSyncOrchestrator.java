package com.aidcompass.core.contact.core.facades;


import com.aidcompass.contracts.ContactType;
import com.aidcompass.core.contact.core.models.dto.system.SystemConfirmationRequestDto;
import com.aidcompass.core.contact.core.models.dto.system.SystemContactCreateDto;
import com.aidcompass.core.contact.core.models.dto.system.SystemContactDto;

public interface ContactServiceSyncOrchestrator {

    void save(SystemContactCreateDto dto);

    Long confirmContact(SystemConfirmationRequestDto requestDto);

    boolean existsByContactTypeAndContact(ContactType type, String contact);

    SystemContactDto findByContact(String contact);
}
