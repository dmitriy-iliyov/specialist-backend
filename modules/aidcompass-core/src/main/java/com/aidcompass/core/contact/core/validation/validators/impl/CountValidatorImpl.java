package com.aidcompass.core.contact.core.validation.validators.impl;

import com.aidcompass.contracts.ContactType;
import com.aidcompass.core.contact.core.models.markers.CreateDto;
import com.aidcompass.core.contact.core.services.SystemContactService;
import com.aidcompass.core.contact.core.validation.validators.CountValidator;
import com.aidcompass.core.contact.exceptions.invalid_input.InvalidContactTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CountValidatorImpl implements CountValidator {

    private final SystemContactService service;

    private static final long MAX_EMAIL_COUNT = 3;
    private static final long MAX_PHONE_NUMBER_COUNT = 2;


    // учитывать и кеш
    @Override
    public boolean hasSpaceForEmails(UUID ownerId, long addCount) {
        long currentCount = service.countByOwnerIdAndContactType(ownerId, ContactType.EMAIL);
        return currentCount + addCount <= MAX_EMAIL_COUNT;
    }

    @Override
    public boolean hasSpaceForPhoneNumbers(UUID ownerId, long addCount) {
        long currentCount = service.countByOwnerIdAndContactType(ownerId, ContactType.PHONE_NUMBER);
        return currentCount + addCount <= MAX_PHONE_NUMBER_COUNT;
    }

    /**
     * Checks if there is space to add a new contact of the specified type for the given owner.
     *
     * @param ownerId ID of the contact owner
     * @param contact DTO containing contact information
     * @return true if there is space to add a new contact of the specified type, false otherwise
     * @throws InvalidContactTypeException if the contact type in the passed DTO does not exist
     */
    @Override
    public boolean hasSpaceForContact(UUID ownerId, CreateDto contact) {
        if (contact.type() == ContactType.EMAIL) {
            return hasSpaceForEmails(ownerId, 1);
        } else if (contact.type() == ContactType.PHONE_NUMBER) {
            return hasSpaceForPhoneNumbers(ownerId, 1);
        } else {
            throw new InvalidContactTypeException();
        }
    }

    @Override
    public boolean hasSpaceForContacts(UUID ownerId, List<CreateDto> contacts) {
        long emailCount = contacts.stream().filter(contact -> contact.type().equals(ContactType.EMAIL)).count();
        long phoneNumberCount = contacts.stream().filter(contact -> contact.type().equals(ContactType.PHONE_NUMBER)).count();
        return hasSpaceForEmails(ownerId, emailCount) && hasSpaceForPhoneNumbers(ownerId, phoneNumberCount);
    }
}
