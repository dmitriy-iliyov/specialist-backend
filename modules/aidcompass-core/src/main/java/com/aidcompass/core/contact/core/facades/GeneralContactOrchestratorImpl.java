package com.aidcompass.core.contact.core.facades;

import com.aidcompass.contracts.ContactConfirmationFacade;
import com.aidcompass.core.contact.core.models.dto.ContactCreateDto;
import com.aidcompass.core.contact.core.models.dto.ContactUpdateDto;
import com.aidcompass.core.contact.core.models.dto.PrivateContactResponseDto;
import com.aidcompass.core.contact.core.models.dto.PublicContactResponseDto;
import com.aidcompass.core.contact.core.models.dto.system.SystemContactDto;
import com.aidcompass.core.contact.core.models.markers.CreateDto;
import com.aidcompass.core.contact.core.services.ContactService;
import com.aidcompass.core.contact.core.validation.validators.CountValidator;
import com.aidcompass.core.contact.core.validation.validators.PermissionValidator;
import com.aidcompass.core.contact.exceptions.SendConfirmationMessageException;
import com.aidcompass.core.contact.exceptions.invalid_input.BaseInvalidContactDeleteException;
import com.aidcompass.core.contact.exceptions.invalid_input.BaseInvalidContactUpdateException;
import com.aidcompass.core.contact.exceptions.invalid_input.InvalidAttemptMarkAsLinkedException;
import com.aidcompass.core.contact.exceptions.invalid_input.NotEnoughSpaseForNewContactExceptionBase;
import com.aidcompass.core.general.exceptions.models.UserNotFoundException;
import com.aidcompass.core.general.exceptions.models.dto.ErrorDto;
import com.aidcompass.core.security.domain.user.services.UserOrchestrator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
@Slf4j
@RequiredArgsConstructor
public class GeneralContactOrchestratorImpl implements GeneralContactOrchestrator {

    private final ContactService contactService;
    private final PermissionValidator permissionValidator;
    private final CountValidator countValidator;

    private final ContactConfirmationFacade contactConfirmationFacade;
    private final UserOrchestrator userOrchestrator;


    @Transactional
    @Override
    public PrivateContactResponseDto save(UUID ownerId, ContactCreateDto dto) {
        boolean isUserExists = userOrchestrator.existsById(ownerId);
        if (!isUserExists) {
            throw new UserNotFoundException();
        }
        if (countValidator.hasSpaceForContact(ownerId, dto)) {
            PrivateContactResponseDto responseDto = contactService.save(ownerId, dto);
            try {
                contactConfirmationFacade.sendConfirmationMessage(responseDto.contact(), responseDto.id(), responseDto.type());
            } catch (Exception e) {
                throw new SendConfirmationMessageException();
            }
            return responseDto;
        }
        throw new NotEnoughSpaseForNewContactExceptionBase(
                List.of(new ErrorDto("contact", "Impossible to add new " + dto.type().toString() + "!"))
        );
    }

    @Override
    public List<PrivateContactResponseDto> saveAll(UUID ownerId, List<ContactCreateDto> contacts) {
        boolean isUserExists = userOrchestrator.existsById(ownerId);
        List<CreateDto> createDtoList = new ArrayList<>(contacts);
        if (isUserExists && countValidator.hasSpaceForContacts(ownerId, createDtoList)) {
            List<PrivateContactResponseDto> dtoList = contactService.saveAll(ownerId, contacts);
            for(PrivateContactResponseDto dto: dtoList) {
                try {
                    contactConfirmationFacade.sendConfirmationMessage(dto.contact(), dto.id(), dto.type());
                } catch (Exception e) {
                    throw new SendConfirmationMessageException();
                }
            }
            return dtoList;
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void requestConfirmation(UUID ownerId, Long contactId) {
        SystemContactDto systemDto = permissionValidator.isConfirmPermit(ownerId, contactId);
        try {
            contactConfirmationFacade.sendConfirmationMessage(systemDto.getContact(), systemDto.getId(), systemDto.getType());
        } catch (Exception e) {
            throw new SendConfirmationMessageException();
        }
    }

    @Override
    public void markEmailAsLinkedToAccount(UUID ownerId, Long contactId) {
        List<ErrorDto> errors = permissionValidator.isLinkingPermit(ownerId, contactId);
        if (!errors.isEmpty()) {
            throw new InvalidAttemptMarkAsLinkedException(errors);
        }
        contactService.markContactAsLinked(ownerId, contactId);
        //оновити лінкований імейл в auth service перевипустити токен аутентификации(перелогін в аккаунт)
    }

    @Override
    public List<PrivateContactResponseDto> findAllPrivateByOwnerId(UUID ownerId) {
        return contactService.findAllPrivateByOwnerId(ownerId);
    }

    @Override
    public List<PublicContactResponseDto> findAllPublicByOwnerId(UUID ownerId) {
        return contactService.findAllPublicByOwnerId(ownerId);
    }

    @Override
    public List<PublicContactResponseDto> findPrimaryByOwnerId(UUID ownerId) {
        return contactService.findPrimaryByOwnerId(ownerId);
    }

    @Override
    public List<PublicContactResponseDto> findSecondaryByOwnerId(UUID ownerId) {
        return contactService.findSecondaryByOwnerId(ownerId);
    }

    @Override
    public PrivateContactResponseDto update(UUID ownerId, ContactUpdateDto contact) {
        List<ErrorDto> errors = permissionValidator.isUpdatePermit(ownerId, contact);
        if (!errors.isEmpty()) {
            throw new BaseInvalidContactUpdateException(errors);
        }
        return contactService.update(ownerId, contact,
                    contactDto -> {
                        try {
                            contactConfirmationFacade.sendConfirmationMessage(
                                    contactDto.contact(), contactDto.id(), contactDto.type()
                            );
                        } catch (Exception e) {
                            throw new SendConfirmationMessageException();
                        }
                    }
                );
    }

    @Override
    public List<PrivateContactResponseDto> updateAll(UUID ownerId, List<ContactUpdateDto> contacts) {
        List<ErrorDto> errors = permissionValidator.isUpdatePermit(ownerId, contacts);
        if (!errors.isEmpty()) {
            throw new BaseInvalidContactUpdateException(errors);
        }
        return contactService.updateAll(ownerId, contacts, contactDto -> {
            try {
                contactConfirmationFacade.sendConfirmationMessage(contactDto.contact(), contactDto.id(), contactDto.type());
            } catch (Exception e) {
                throw new SendConfirmationMessageException();
            }
        });
    }

    @Override
    public void delete(UUID ownerId, Long contactId) {
        List<ErrorDto> errors = permissionValidator.isDeletePermit(ownerId, contactId);
        if (!errors.isEmpty()) {
            throw new BaseInvalidContactDeleteException(errors);
        }
        contactService.deleteById(ownerId, contactId);
    }

    @Override
    public void deleteAll(UUID ownerId) {
        contactService.deleteAll(ownerId);
    }
}