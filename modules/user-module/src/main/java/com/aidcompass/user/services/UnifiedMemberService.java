package com.aidcompass.user.services;

import com.aidcompass.user.MemberMapper;
import com.aidcompass.user.exceptions.MemberNotFoundByIdException;
import com.aidcompass.user.models.MemberEntity;
import com.aidcompass.user.models.ScopeType;
import com.aidcompass.user.models.dto.MemberCreateDto;
import com.aidcompass.user.models.dto.MemberUpdateDto;
import com.aidcompass.user.models.dto.PrivateMemberResponseDto;
import com.aidcompass.user.models.dto.PublicMemberResponseDto;
import com.aidcompass.user.repositories.MemberRepository;
import com.aidcompass.utils.pagination.PageRequest;
import com.aidcompass.utils.pagination.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnifiedMemberService implements MemberService, UserRatingService {

    private final MemberRepository repository;
    private final MemberMapper mapper;


    @Transactional
    @Override
    public PrivateMemberResponseDto save(MemberCreateDto dto) {
        MemberEntity userEntity = mapper.toEntity(dto);
        return mapper.toPrivateDto(userEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public PrivateMemberResponseDto findPrivateById(UUID id) {
        return mapper.toPrivateDto(repository.findById(id).orElseThrow(MemberNotFoundByIdException::new));
    }

    @Transactional
    @Override
    public PrivateMemberResponseDto update(MemberUpdateDto dto) {
        MemberEntity userEntity = repository.findById(dto.getId()).orElseThrow(MemberNotFoundByIdException::new);
        mapper.updateEntityFromDto(dto, userEntity);
        return mapper.toPrivateDto(repository.save(userEntity));
    }

    @Transactional(readOnly = true)
    @Override
    public PublicMemberResponseDto findPublicById(UUID id) {
        return mapper.toPublicDto(repository.findById(id).orElseThrow(MemberNotFoundByIdException::new));
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<?> findAll(ScopeType scope, PageRequest page) {
        Pageable pageable;
        if (page.asc() != null && page.asc()) {
            pageable = org.springframework.data.domain.PageRequest.of(
                    page.pageNumber(), page.pageSize(), Sort.by("creatorRating").ascending()
            );
        } else {
            pageable = org.springframework.data.domain.PageRequest.of(
                    page.pageNumber(), page.pageSize(), Sort.by("creatorRating").descending()
            );
        }
        Page<MemberEntity> entityPage = repository.findAll(pageable);
        if (scope.equals(ScopeType.PUBLIC)) {
            return new PageResponse<>(
                    mapper.toPublicDtoList(entityPage.getContent()),
                    entityPage.getTotalPages()
            );
        }
        return new PageResponse<>(
                mapper.toPrivateDtoList(entityPage.getContent()),
                entityPage.getTotalPages()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Map<UUID, PublicMemberResponseDto> findAllByIdIn(Set<UUID> ids) {
        List<MemberEntity> entityList = repository.findAllByIdIn(ids);
        return mapper.toPublicDtoList(entityList).stream()
                .collect(Collectors.toMap(PublicMemberResponseDto::getId, Function.identity()));
    }

    @Transactional
    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
