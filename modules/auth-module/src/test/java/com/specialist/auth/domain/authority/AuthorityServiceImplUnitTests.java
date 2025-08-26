package com.specialist.auth.domain.authority;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorityServiceImplUnitTests {

    @Mock
    private AuthorityRepository repository;

    @Mock
    private AuthorityCacheService cacheService;

    @InjectMocks
    private AuthorityServiceImpl service;

    @Test
    @DisplayName("UT: getReferenceAllByAuthorityIn() when authorities exist should return list")
    void getReferenceAllByAuthorityIn_whenAuthoritiesExist_shouldReturnList() {
        List<Authority> authorities = List.of(Authority.REVIEW_CREATE_UPDATE);
        List<AuthorityEntity> expected = List.of(new AuthorityEntity());

        when(cacheService.getAuthoritiesIds(authorities)).thenReturn(null);
        when(repository.getReferenceAllByAuthorityIn(authorities)).thenReturn(expected);

        List<AuthorityEntity> result = service.getReferenceAllByAuthorityIn(authorities);

        verify(repository, times(1)).getReferenceAllByAuthorityIn(authorities);
        verifyNoMoreInteractions(repository);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("UT: getReferenceAllByAuthorityIn() when empty list passed should return empty list")
    void getReferenceAllByAuthorityIn_whenEmpty_shouldReturnEmptyList() {
        List<Authority> authorities = Collections.emptyList();

        when(cacheService.getAuthoritiesIds(authorities)).thenReturn(null);
        when(repository.getReferenceAllByAuthorityIn(authorities)).thenReturn(Collections.emptyList());

        List<AuthorityEntity> result = service.getReferenceAllByAuthorityIn(authorities);

        verify(repository, times(1)).getReferenceAllByAuthorityIn(authorities);
        verifyNoMoreInteractions(repository);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("UT: findAllByAccountIdIn() should convert pairs to map")
    void findAllByAccountIdIn_shouldConvertPairsToMap() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        AuthorityEntity auth1 = mock(AuthorityEntity.class);
        AuthorityEntity auth2 = mock(AuthorityEntity.class);

        when(auth1.getAuthorityAsEnum()).thenReturn(Authority.REVIEW_CREATE_UPDATE);
        when(auth2.getAuthorityAsEnum()).thenReturn(Authority.TYPE_SUGGEST);

        List<Object[]> pairs = List.of(
                new Object[]{id1, auth1},
                new Object[]{id1, auth2},
                new Object[]{id2, auth1}
        );

        when(repository.findAllByAccountIdIn(Set.of(id1, id2))).thenReturn(pairs);

        Map<UUID, List<Authority>> result = service.findAllByAccountIdIn(Set.of(id1, id2));

        verify(repository, times(1)).findAllByAccountIdIn(Set.of(id1, id2));
        verifyNoMoreInteractions(repository);
        verify(auth1, times(2)).getAuthorityAsEnum();
        verify(auth2, times(1)).getAuthorityAsEnum();
        verifyNoMoreInteractions(auth1, auth2);

        assertEquals(2, result.size());
        assertEquals(List.of(Authority.REVIEW_CREATE_UPDATE, Authority.TYPE_SUGGEST), result.get(id1));
        assertEquals(List.of(Authority.REVIEW_CREATE_UPDATE), result.get(id2));
    }

    @Test
    @DisplayName("UT: findAllByAccountIdIn() when repository returns empty list should return empty map")
    void findAllByAccountIdIn_whenEmpty_shouldReturnEmptyMap() {
        Set<UUID> ids = Set.of(UUID.randomUUID());
        when(repository.findAllByAccountIdIn(ids)).thenReturn(Collections.emptyList());

        Map<UUID, List<Authority>> result = service.findAllByAccountIdIn(ids);

        verify(repository, times(1)).findAllByAccountIdIn(ids);
        verifyNoMoreInteractions(repository);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("UT: findAllByServiceAccountIdIn() should convert pairs to map")
    void findAllByServiceAccountIdIn_shouldConvertPairsToMap() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        AuthorityEntity auth1 = mock(AuthorityEntity.class);
        AuthorityEntity auth2 = mock(AuthorityEntity.class);
        when(auth1.getAuthorityAsEnum()).thenReturn(Authority.SPECIALIST_CREATE_UPDATE);
        when(auth2.getAuthorityAsEnum()).thenReturn(Authority.SPECIALIST_CREATE_UPDATE);

        List<Object[]> pairs = new ArrayList<>(List.of(new Object [] {id1, auth1}, new Object [] {id2, auth2}));

        when(repository.findAllByServiceAccountIdIn(Set.of(id1, id2))).thenReturn(pairs);

        Map<UUID, List<Authority>> result = service.findAllByServiceAccountIdIn(Set.of(id1, id2));

        verify(repository, times(1)).findAllByServiceAccountIdIn(Set.of(id1, id2));
        verifyNoMoreInteractions(repository);
        verify(auth1, times(1)).getAuthorityAsEnum();
        verify(auth2, times(1)).getAuthorityAsEnum();
        verifyNoMoreInteractions(auth1);
        verifyNoMoreInteractions(auth2);

        assertEquals(2, result.size());
        assertEquals(List.of(Authority.SPECIALIST_CREATE_UPDATE), result.get(id1));
    }

    @Test
    @DisplayName("UT: findAllByServiceAccountIdIn() when empty should return empty map")
    void findAllByServiceAccountIdIn_whenEmpty_shouldReturnEmptyMap() {
        Set<UUID> ids = Set.of(UUID.randomUUID());
        when(repository.findAllByServiceAccountIdIn(ids)).thenReturn(Collections.emptyList());

        Map<UUID, List<Authority>> result = service.findAllByServiceAccountIdIn(ids);

        verify(repository, times(1)).findAllByServiceAccountIdIn(ids);
        verifyNoMoreInteractions(repository);
        assertTrue(result.isEmpty());
    }
}
