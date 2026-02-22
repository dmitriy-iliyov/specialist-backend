package com.specialist.auth.domain.authority;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorityServiceImplUnitTests {

    @Mock
    private AuthorityRepository repository;

    @Mock
    private AuthorityCacheService cacheService;

    @InjectMocks
    private AuthorityServiceImpl service;

    @Test
    @DisplayName("UT: getReferenceAllByAuthorityIn() when cache hit should return references")
    void getReferenceAllByAuthorityIn_cacheHit_shouldReturnReferences() {
        List<Authority> authorities = List.of(Authority.ACCOUNT_CREATE);
        List<Long> ids = new ArrayList<>(List.of(1L));
        AuthorityEntity entity = new AuthorityEntity();
        
        when(cacheService.getAuthoritiesIds(authorities)).thenReturn(ids);
        when(repository.getReferenceById(1L)).thenReturn(entity);

        List<AuthorityEntity> result = service.getReferenceAllByAuthorityIn(authorities);

        assertEquals(1, result.size());
        assertEquals(entity, result.get(0));
        verify(cacheService).getAuthoritiesIds(authorities);
        verify(repository).getReferenceById(1L);
        verify(repository, never()).getReferenceAllByAuthorityIn(any());
        verifyNoMoreInteractions(cacheService, repository);
    }

    @Test
    @DisplayName("UT: getReferenceAllByAuthorityIn() when cache miss should call repository")
    void getReferenceAllByAuthorityIn_cacheMiss_shouldCallRepository() {
        List<Authority> authorities = List.of(Authority.ACCOUNT_CREATE);
        when(cacheService.getAuthoritiesIds(authorities)).thenReturn(Collections.emptyList());
        
        service.getReferenceAllByAuthorityIn(authorities);

        verify(cacheService).getAuthoritiesIds(authorities);
        verify(repository).getReferenceAllByAuthorityIn(authorities);
        verifyNoMoreInteractions(cacheService, repository);
    }

    @Test
    @DisplayName("UT: findAllByAccountIdIn() should map results correctly")
    void findAllByAccountIdIn_shouldMapResults() {
        UUID accountId = UUID.randomUUID();
        AuthorityEntity authorityEntity = new AuthorityEntity(Authority.ACCOUNT_CREATE);
        Object [] pair = new Object[]{accountId, authorityEntity};
        List<Object[]> pairs = new ArrayList<>();
        pairs.add(pair);

        when(repository.findAllByAccountIdIn(Set.of(accountId))).thenReturn(pairs);

        Map<UUID, List<Authority>> result = service.findAllByAccountIdIn(Set.of(accountId));

        assertEquals(1, result.size());
        assertEquals(Authority.ACCOUNT_CREATE, result.get(accountId).get(0));
        verify(repository).findAllByAccountIdIn(Set.of(accountId));
        verifyNoMoreInteractions(repository);
    }
}
