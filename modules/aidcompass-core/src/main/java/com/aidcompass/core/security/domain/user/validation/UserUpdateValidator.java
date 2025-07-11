package com.aidcompass.core.security.domain.user.validation;


import com.aidcompass.contracts.AccountResourceConfirmationService;
import com.aidcompass.contracts.ContactType;
import com.aidcompass.core.contact.core.facades.ContactServiceSyncOrchestrator;
import com.aidcompass.core.contact.core.facades.SystemContactFacade;
import com.aidcompass.core.contact.core.models.dto.system.SystemContactUpdateDto;
import com.aidcompass.core.contact.exceptions.SendConfirmationMessageException;
import com.aidcompass.core.general.exceptions.models.BaseNotFoundException;
import com.aidcompass.core.security.domain.user.models.dto.SystemUserUpdateDto;
import com.aidcompass.core.security.domain.authority.models.Authority;
import com.aidcompass.core.security.domain.user.models.dto.SystemUserDto;
import com.aidcompass.core.security.domain.user.services.UserOrchestrator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

// move somewhere because shouldn't be in annotation validator
@RequiredArgsConstructor
public class UserUpdateValidator implements ConstraintValidator<UserUpdate, SystemUserUpdateDto> {

    private final UserOrchestrator userOrchestrator;
    private final AccountResourceConfirmationService confirmationService;
    private final ContactServiceSyncOrchestrator synchronizationFacade;
    private final SystemContactFacade systemContactFacade;


    @Override
    public boolean isValid(SystemUserUpdateDto dto, ConstraintValidatorContext constraintValidatorContext)
            throws IllegalArgumentException, EntityNotFoundException {

        if (userOrchestrator == null) {
            throw new IllegalStateException("UserFacade is not properly initialized.");
        }

        boolean hasErrors = false;

        if(dto == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("User not passed!")
                    .addPropertyNode("user")
                    .addConstraintViolation();
            return false;
        }

        try {
            boolean isEmailExist = synchronizationFacade.existsByContactTypeAndContact(ContactType.EMAIL, dto.getEmail());
            if(isEmailExist) {
                hasErrors = true;
                constraintValidatorContext.buildConstraintViolationWithTemplate("Email already in use!")
                        .addPropertyNode("email")
                        .addConstraintViolation();
            } else {
                SystemUserDto existedUser = userOrchestrator.systemFindByEmail(dto.getEmail());
                dto.setAuthorities(existedUser.authorities());
            }
        } catch (BaseNotFoundException e) {

            //
            SystemUserDto systemUserDto = userOrchestrator.systemFindById(dto.getId());
            List<Authority> authorities = systemUserDto.authorities();
            authorities.remove(Authority.ROLE_USER);
            authorities.add(Authority.ROLE_UNCONFIRMED_USER);
            //

            systemContactFacade.update(
                    new SystemContactUpdateDto(
                            systemUserDto.id(),
                            systemUserDto.emailId(),
                            ContactType.EMAIL,
                            dto.getEmail(),
                            true)
            );
            try {
                confirmationService.sendConfirmationMessage(dto.getEmail());
            } catch (Exception ex) {
                throw new SendConfirmationMessageException();
            }

            dto.setAuthorities(authorities);
            return true;
        }
        return !hasErrors;
    }
}
