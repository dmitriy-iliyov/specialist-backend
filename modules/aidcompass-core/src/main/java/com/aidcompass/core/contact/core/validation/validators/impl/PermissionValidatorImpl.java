package com.aidcompass.core.contact.core.validation.validators.impl;

import com.aidcompass.core.contact.core.models.dto.ContactUpdateDto;
import com.aidcompass.core.contact.core.models.dto.system.SystemContactDto;
import com.aidcompass.core.contact.core.models.markers.UpdateDto;
import com.aidcompass.core.contact.core.services.SystemContactService;
import com.aidcompass.core.contact.core.validation.validators.ContactOwnershipValidator;
import com.aidcompass.core.contact.core.validation.validators.ContactUniquenessValidator;
import com.aidcompass.core.contact.core.validation.validators.FormatValidator;
import com.aidcompass.core.contact.core.validation.validators.PermissionValidator;
import com.aidcompass.core.contact.exceptions.invalid_input.DoubleContactIdException;
import com.aidcompass.core.contact.exceptions.invalid_input.InvalidAttemptChangeToPrimaryException;
import com.aidcompass.core.contact.exceptions.invalid_input.OwnerShipException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PermissionValidatorImpl implements PermissionValidator {

    private final SystemContactService service;
    private final ContactOwnershipValidator contactOwnershipValidator;
    private final ContactUniquenessValidator contactUniquenessValidator;
    private final FormatValidator formatValidator;

    /**
     * Validates a list of contact updates to ensure no permitted changes are made.
     *
     * @param ownerId ID of the contact owner
     * @param dtoList List of contact update DTOs
     * @return List of errors found during validation
     * @throws DoubleContactIdException if duplicate contact IDs are found in the list
     * @throws OwnerShipException if one of passed contacts isn't owners
     * @throws InvalidAttemptChangeToPrimaryException if an attempt is made to set an unconfirmed contact as primary
     */
    @Override
    public List<ErrorDto> isUpdatePermit(UUID ownerId, List<ContactUpdateDto> dtoList) {
        List<ErrorDto> errors = new ArrayList<>();
        Set<Long> contactIds = dtoList.stream()
                .map(ContactUpdateDto::id)
                .collect(Collectors.toSet());

        // check for duplicate contacts id
        if (contactIds.size() != dtoList.size()) {
            throw new DoubleContactIdException();
        }

        List<SystemContactDto> systemContactDtoList = service.findAllByOwnerId(ownerId);

        // check ownership
        contactOwnershipValidator.assertOwnership(contactIds.stream().toList(), systemContactDtoList);

        Map<Long, SystemContactDto> mapOfIdDto = systemContactDtoList.stream()
                .collect(Collectors.toMap(
                        SystemContactDto::getId,
                        Function.identity())
                );

        for (ContactUpdateDto contactDto: dtoList) {
            SystemContactDto systemDto = mapOfIdDto.get(contactDto.id());
            // check if an unconfirmed contact is being set as primary
            if (!systemDto.isConfirmed() && contactDto.isPrimary()) {
                throw new InvalidAttemptChangeToPrimaryException();
            }

            // check uniqueness of the contact
            contactUniquenessValidator.checkUniquesForType(ownerId, contactDto, errors);
        }

        return errors;
    }

    /**
     * Validates a  contact update to ensure no permitted changes are made.
     *
     * @param ownerId ID of the contact owner
     * @param updateDto contact update DTO
     * @return List of errors found during validation
     * @throws OwnerShipException if passed contact isn't owners
     * @throws InvalidAttemptChangeToPrimaryException if an attempt is made to set an unconfirmed contact as primary
     */
    @Override
    public List<ErrorDto> isUpdatePermit(UUID ownerId, UpdateDto updateDto) {
        List<ErrorDto> errors = new ArrayList<>();

        SystemContactDto systemContactDto = service.findById(updateDto.id());
        if (!systemContactDto.getOwnerId().equals(ownerId)) {
            throw new OwnerShipException(new ErrorDto("contact_id", "Contact isn't yours!"));
        }

        if (!systemContactDto.isConfirmed() && updateDto.isPrimary()) {
            throw new InvalidAttemptChangeToPrimaryException();
        }

        contactUniquenessValidator.checkUniquesForType(ownerId, updateDto, errors);

        return errors;
    }

    @Override
    public List<ErrorDto> isDeletePermit(UUID ownerId, Long contactId) {
        List<ErrorDto> errors = new ArrayList<>();

        SystemContactDto systemContactDto = service.findById(contactId);
        if (systemContactDto.getOwnerId() != ownerId) {
            throw new OwnerShipException(new ErrorDto("contact_id", "Contact isn't yours!"));
        }

        if (systemContactDto.isLinkedToAccount()) {
            errors.add(new ErrorDto("contact", "This contact is linked to account, that's why it can't be deleted!"));
        }

        return errors;
    }

    @Override
    public List<ErrorDto> isLinkingPermit(UUID ownerId, Long contactId) {
        List<ErrorDto> errors = new ArrayList<>();

        SystemContactDto systemContactDto = service.findById(contactId);
        if (systemContactDto.getOwnerId() != ownerId) {
            throw new OwnerShipException(new ErrorDto("contact_id", "Contact isn't yours!"));
        }

        if (!formatValidator.isEmailValid(systemContactDto.getContact())) {
            errors.add(new ErrorDto("contact", "Only email can be linked to account!"));
        }

        if (!systemContactDto.isConfirmed()) {
            errors.add(new ErrorDto("contact", "Unconfirmed email can't be linked to account!"));
        }

        return errors;
    }

    @Override
    public SystemContactDto isConfirmPermit(UUID ownerId, Long contactId) {
        SystemContactDto systemContactDto = service.findById(contactId);
        if (systemContactDto.getOwnerId() != ownerId) {
            throw new OwnerShipException(new ErrorDto("contact_id", "Contact isn't yours!"));
        }
        return systemContactDto;
    }
}
