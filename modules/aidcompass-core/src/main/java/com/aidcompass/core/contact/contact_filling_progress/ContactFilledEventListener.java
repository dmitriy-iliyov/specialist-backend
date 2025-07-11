package com.aidcompass.core.contact.contact_filling_progress;

import com.aidcompass.core.contact.contact_filling_progress.models.ContactFilledEvent;
import com.aidcompass.core.general.contracts.ProfileStatusUpdateFacade;
import com.aidcompass.core.general.contracts.enums.RoleToServiceTypeMapper;
import com.aidcompass.core.general.contracts.enums.ServiceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class ContactFilledEventListener {

    private final ProfileStatusUpdateFacade profileStatusUpdateFacade;


    @TransactionalEventListener
    public void notifyAboutComplete(ContactFilledEvent event) {
        ServiceType type = RoleToServiceTypeMapper.from(event.authority());
        profileStatusUpdateFacade.updateProfileStatus(type, event.userId());
    }
}
