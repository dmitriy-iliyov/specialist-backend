package com.aidcompass.core.contact.core.validation.validators;

import com.aidcompass.core.contact.core.models.dto.system.SystemContactDto;

import java.util.List;

public interface ContactOwnershipValidator {

    void assertOwnership(List<Long> contactIdList, List<SystemContactDto> systemContactDtoList);
}
