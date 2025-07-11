package com.aidcompass.users.customer.repository;

import com.aidcompass.users.customer.models.CustomerEntity;
import com.aidcompass.users.profile_status.models.ProfileStatusEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID>,
                                            JpaSpecificationExecutor<CustomerEntity> {

    @EntityGraph(attributePaths = {"profileStatusEntity"})
    @NonNull
    Optional<CustomerEntity> findById(@NonNull UUID id);

    @EntityGraph(attributePaths = {"profileStatusEntity"})
    Optional<CustomerEntity> findWithProfileStatusById(UUID id);

    @Modifying
    @Query("""
            UPDATE CustomerEntity c
            SET c.profileProgress = c.profileProgress + :profileProgressStep,
                c.profileStatusEntity = :profileStatusEntity
            WHERE c.id = :id
    """)
    void updateProgressAndProfileStatus(@Param("id") UUID id, @Param("profileProgressStep") int profileProgressStep,
                                        @Param("profileStatusEntity") ProfileStatusEntity profileStatusEntity);

    @Modifying
    @Query("""
            UPDATE CustomerEntity c
            SET c.profileProgress = c.profileProgress + :profileProgressStep
            WHERE c.id = :id
    """)
    void updateProfileProgress(@Param("id") UUID id, @Param("profileProgressStep") int profileProgressStep);

    @EntityGraph(attributePaths = {"profileStatusEntity"})
    @NonNull
    Page<CustomerEntity> findAll(Specification<CustomerEntity> specification, @NonNull Pageable pageable);
}