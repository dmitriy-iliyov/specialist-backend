package com.aidcompass.user.services;

import com.aidcompass.user.AccountService;
import com.aidcompass.user.models.dto.MemberUpdateDto;
import com.aidcompass.user.models.dto.PrivateMemberResponseDto;
import com.aidcompass.user.models.dto.MemberCreateDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class MemberOrchestratorImpl implements MemberOrchestrator {

    private final MemberService memberService;
    private final ProfileImgService profileImgService;
    private final Validator validator;
    private final AccountService accountService;


    @Transactional
    @Override
    public PrivateMemberResponseDto save(MemberCreateDto dto) {
        if (!dto.getAvatar().isEmpty()) {
            dto.setAvatarUrl(profileImgService.save(dto.getAvatar(), dto.getId()));
        }
        return memberService.save(dto);
    }

    @Transactional
    @Override
    public PrivateMemberResponseDto update(MemberUpdateDto dto) {
        Set<ConstraintViolation<MemberUpdateDto>> errors = validator.validate(dto);
        if (!errors.isEmpty()) {
            throw new ConstraintViolationException(errors);
        }
        if (!dto.getAvatar().isEmpty()) {
            dto.setAvatarUrl(profileImgService.save(dto.getAvatar(), dto.getId()));
        }
        PrivateMemberResponseDto responseDto = memberService.update(dto);
        accountService.updateEmailById(responseDto.getId());
        return responseDto;
    }
}
