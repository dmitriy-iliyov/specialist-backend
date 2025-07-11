package com.aidcompass.core.contact.core.facades;

import com.aidcompass.core.contact.core.models.dto.system.SystemContactDto;
import com.aidcompass.core.contact.core.models.dto.system.SystemContactUpdateDto;
import com.aidcompass.core.contact.core.services.SystemContactService;
import com.aidcompass.core.contact.core.validation.validators.PermissionValidator;
import com.aidcompass.core.contact.exceptions.invalid_input.BaseInvalidContactUpdateException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SystemContactFacadeImpl implements SystemContactFacade {

    private final SystemContactService systemContactService;
    private final PermissionValidator permissionValidator;


    @Override
    public void confirmContactById(Long contactId) {
        systemContactService.confirmContactById(contactId);
    }

    @Override
    public SystemContactDto update(SystemContactUpdateDto dto) {
        List<ErrorDto> errors = permissionValidator.isUpdatePermit(dto.ownerId(), dto);
        if (!errors.isEmpty()) {
            throw new BaseInvalidContactUpdateException(errors);
        }
        return systemContactService.update(dto);
    }
}
