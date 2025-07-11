package com.aidcompass.users.jurist.specialization;

import com.aidcompass.users.jurist.specialization.models.JuristSpecialization;
import com.aidcompass.users.jurist.specialization.models.JuristSpecializationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JuristSpecializationRepository extends JpaRepository<JuristSpecializationEntity, Integer> {

    Optional<JuristSpecializationEntity> findBySpecialization(JuristSpecialization specialization);

    @Query("""
                SELECT j.id, s FROM JuristSpecializationEntity s
                JOIN FETCH s.jurists j
                WHERE j.id IN :ids
    """)
    List<Object[]> findAllPairsByJuristIdIn(@Param("ids") List<UUID> ids);

}
