package com.aidcompass.user.services;

import com.aidcompass.user.models.dto.MemberUpdateDto;
import com.aidcompass.user.models.dto.PrivateMemberResponseDto;
import com.aidcompass.user.models.dto.MemberCreateDto;

public interface MemberOrchestrator {
    PrivateMemberResponseDto save(MemberCreateDto dto);

    PrivateMemberResponseDto update(MemberUpdateDto dto);
}
