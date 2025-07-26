package com.aidcompass.user.validation;

import com.aidcompass.user.AccountService;
import com.aidcompass.user.models.dto.MemberUpdateDto;
import com.aidcompass.user.models.dto.PrivateMemberResponseDto;
import com.aidcompass.user.services.MemberService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class EmailUniquenessValidator implements ConstraintValidator<UniqueEmail, MemberUpdateDto> {

    private final AccountService accountService;
    private final MemberService userService;


    @Override
    public boolean isValid(MemberUpdateDto dto, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (dto == null) {
            context.buildConstraintViolationWithTemplate("User is required.")
                    .addPropertyNode("user")
                    .addConstraintViolation();
            return false;
        }

        PrivateMemberResponseDto existsUser = userService.findPrivateById(dto.getId());
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
