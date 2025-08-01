package com.aidcompass.auth.domain.authority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
    List<AuthorityEntity> getReferenceAllByAuthorityIn(List<Authority> authorities);

    @Query("""
        SELECT accs.id, a FROM AuthorityEntity a
        JOIN a.accounts accs
        WHERE accs.id IN :ids
    """)
    List<Object[]> findAllByAccountIdIn(@Param("ids") Set<UUID> accountIds);
}
