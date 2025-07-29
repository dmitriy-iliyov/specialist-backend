package com.aidcompass.user.validation;

import com.aidcompass.contracts.auth.SystemAccountService;
import com.aidcompass.user.models.dtos.PrivateUserResponseDto;
import com.aidcompass.user.models.dtos.UserUpdateDto;
import com.aidcompass.user.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class EmailUniquenessValidator implements ConstraintValidator<UniqueEmail, UserUpdateDto> {

    private final SystemAccountService accountService;
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
            UUID emailOwnerId = accountService.findIdByEmail(dto.getEmail());
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
