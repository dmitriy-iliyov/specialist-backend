package com.aidcompass.core.contact.core.validation.validators.impl;

import com.aidcompass.contracts.ContactType;
import com.aidcompass.core.contact.core.facades.ContactServiceSyncOrchestrator;
import com.aidcompass.core.contact.core.models.markers.UpdateDto;
import com.aidcompass.core.contact.core.validation.validators.ContactUniquenessValidator;
import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class ContactUniquenessValidatorImpl implements ContactUniquenessValidator {

    private final ContactServiceSyncOrchestrator orchestrator;


    @Override
    public boolean isEmailUnique(String email) {
        // check in userUnconfirmedRepo
        return !orchestrator.existsByContactTypeAndContact(ContactType.EMAIL, email);
    }

    @Override
    public boolean isEmailUnique(String email, UUID ownerId) {
        try {
            // check in userUnconfirmedRepo
            return orchestrator.findByContact(email).getOwnerId() != ownerId;
        } catch (BaseNotFoundException e) {
            return true;
        }
    }

    @Override
    public boolean isPhoneNumberUnique(String phoneNumber, UUID ownerId) {
        try {
            return orchestrator.findByContact(phoneNumber).getOwnerId() != ownerId;
        } catch (BaseNotFoundException e) {
            return true;
        }
    }

    @Override
    public boolean isPhoneNumberUnique(String phoneNumber) {
        return !orchestrator.existsByContactTypeAndContact(ContactType.PHONE_NUMBER, phoneNumber);
    }

    /**
     * Validates the uniqueness of a contact based on its type and value for the given owner.
     *
     * @param ownerId ID of the contact owner
     * @param contactUpdateDto DTO containing updated contact information
     * @param errors List to accumulate validation errors
     */
    @Override
    public void checkUniquesForType(UUID ownerId, UpdateDto contactUpdateDto, List<ErrorDto> errors) {
        String contact = contactUpdateDto.contact();
        ContactType type = contactUpdateDto.type();

        if (type == ContactType.EMAIL) {
            if (!this.isEmailUnique(contact, ownerId)) {
                errors.add(new ErrorDto("email", "Email is in use!"));
            }
        } else if (type == ContactType.PHONE_NUMBER) {
            if (!this.isPhoneNumberUnique(contact, ownerId)) {
                errors.add(new ErrorDto("phone_number", "Phone number is in use!"));
            }
        } else {
            errors.add(new ErrorDto("type", "Contact type is invalid!"));
        }
    }
}
