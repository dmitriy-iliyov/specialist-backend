package com.aidcompass.core.contact.core.validation.validators;

import com.aidcompass.core.contact.core.models.dto.ContactUpdateDto;
import com.aidcompass.core.contact.core.models.dto.system.SystemContactDto;
import com.aidcompass.core.contact.core.models.markers.UpdateDto;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

import java.util.List;
import java.util.UUID;

public interface PermissionValidator {
    List<ErrorDto> isUpdatePermit(UUID ownerId, List<ContactUpdateDto> contactUpdateDtoList);

    List<ErrorDto> isUpdatePermit(UUID ownerId, UpdateDto contactUpdateDto);

    List<ErrorDto> isDeletePermit(UUID ownerId, Long id);

    List<ErrorDto> isLinkingPermit(UUID ownerId, Long id);

    SystemContactDto isConfirmPermit(UUID ownerId, Long contactId);
}
