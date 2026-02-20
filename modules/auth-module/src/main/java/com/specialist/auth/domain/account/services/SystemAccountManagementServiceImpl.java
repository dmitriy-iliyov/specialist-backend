package com.specialist.auth.domain.account.services;

import com.specialist.auth.domain.account.mappers.AccountMapper;
import com.specialist.auth.domain.account.models.AccountEntity;
import com.specialist.auth.domain.account.models.dtos.ShortAccountResponseDto;
import com.specialist.auth.domain.account.repositories.AccountRepository;
import com.specialist.auth.domain.authority.Authority;
import com.specialist.auth.exceptions.AccountNotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SystemAccountManagementServiceImpl implements SystemAccountManagementService {

    private final AccountRepository repository;
    private final AccountMapper mapper;

    @Transactional
    @Override
    public ShortAccountResponseDto takeAwayAuthoritiesById(UUID id, Set<Authority> authorities) {
        AccountEntity accountEntity = repository.findById(id).orElseThrow(AccountNotFoundByIdException::new);
        accountEntity.getAuthorities().removeIf(entity -> authorities.contains(entity.getAuthorityAsEnum()));
        accountEntity = repository.save(accountEntity);
        return mapper.toShortResponseDto(accountEntity);
    }
}
