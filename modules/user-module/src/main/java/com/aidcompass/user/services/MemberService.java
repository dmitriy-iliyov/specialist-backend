package com.aidcompass.user.services;

import com.aidcompass.user.models.ScopeType;
import com.aidcompass.user.models.dto.MemberCreateDto;
import com.aidcompass.user.models.dto.MemberUpdateDto;
import com.aidcompass.user.models.dto.PrivateMemberResponseDto;
import com.aidcompass.user.models.dto.PublicMemberResponseDto;
import com.aidcompass.utils.pagination.PageRequest;
import com.aidcompass.utils.pagination.PageResponse;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface MemberService {
    PrivateMemberResponseDto save(MemberCreateDto dto);

    PrivateMemberResponseDto findPrivateById(UUID id);

    PrivateMemberResponseDto update(MemberUpdateDto dto);

    PublicMemberResponseDto findPublicById(UUID id);

    PageResponse<?> findAll(ScopeType scope, PageRequest page);

    Map<UUID, PublicMemberResponseDto> findAllByIdIn(Set<UUID> ids);

    void deleteById(UUID id);
}
