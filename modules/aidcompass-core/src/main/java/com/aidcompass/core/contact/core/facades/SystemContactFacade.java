package com.aidcompass.core.contact.core.facades;


import com.aidcompass.core.contact.core.models.dto.system.SystemContactDto;
import com.aidcompass.core.contact.core.models.dto.system.SystemContactUpdateDto;

public interface SystemContactFacade {

    void confirmContactById(Long contactId);

    SystemContactDto update(SystemContactUpdateDto dto);
}
