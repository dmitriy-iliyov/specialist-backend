package com.specialist.user.validation;

import com.specialist.contracts.auth.SystemAccountFacade;
import com.specialist.user.models.dtos.PrivateUserResponseDto;
import com.specialist.user.models.dtos.UserUpdateDto;
import com.specialist.user.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class EmailUniquenessValidator implements ConstraintValidator<UniqueEmail, UserUpdateDto> {

    private final SystemAccountFacade accountFacade;
    private final UserService userService;

    @Override
    public boolean isValid(UserUpdateDto dto, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (dto == null) {
            context.buildConstraintViolationWithTemplate("User is required.")
                    .addPropertyNode("user")
                    .addConstraintViolation();
            return false;
        }

        PrivateUserResponseDto existsUser = userService.findPrivateById(dto.getId());
        if (existsUser.getEmail().equals(dto.getEmail())) {
            return true;
        } else {
            UUID emailOwnerId = accountFacade.findIdByEmail(dto.getEmail());
            if (emailOwnerId != null && !existsUser.getId().equals(emailOwnerId)) {
                context.buildConstraintViolationWithTemplate("Email isn't unique.")
                        .addPropertyNode("email")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
