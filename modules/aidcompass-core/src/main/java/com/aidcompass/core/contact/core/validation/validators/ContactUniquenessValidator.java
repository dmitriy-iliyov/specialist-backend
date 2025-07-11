package com.aidcompass.core.contact.core.validation.validators;

import com.aidcompass.core.contact.core.models.markers.UpdateDto;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;

import java.util.List;
import java.util.UUID;

public interface ContactUniquenessValidator {
    boolean isEmailUnique(String email);

    boolean isEmailUnique(String email, UUID ownerId);

    boolean isPhoneNumberUnique(String phoneNumber, UUID ownerId);

    boolean isPhoneNumberUnique(String phoneNumber);

    void checkUniquesForType(UUID ownerId, UpdateDto contactUpdateDto, List<ErrorDto> errors);
}
