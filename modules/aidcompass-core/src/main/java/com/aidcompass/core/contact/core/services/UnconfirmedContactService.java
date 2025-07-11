package com.aidcompass.core.contact.core.services;

import com.aidcompass.core.contact.core.models.dto.system.SystemContactCreateDto;
import com.aidcompass.core.contact.core.models.dto.system.SystemContactDto;

public interface UnconfirmedContactService {

    void save(SystemContactCreateDto dto);

    SystemContactDto findById(String contact);

    void deleteById(String contact);

    boolean existsById(String contact);
}
