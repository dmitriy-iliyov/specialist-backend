package com.aidcompass.users.jurist.repository;

import com.aidcompass.users.gender.Gender;
import com.aidcompass.users.jurist.models.JuristEntity;
import com.aidcompass.users.profile_status.models.ProfileStatusEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface JuristRepository extends JpaRepository<JuristEntity, UUID>,
                                          JpaSpecificationExecutor<JuristEntity> {

    @Modifying
    @Query("""
            UPDATE JuristEntity j 
            SET j.isApproved = true 
            WHERE j.id = :id
    """)
    void approveById(@Param("id") UUID id);


    @Query("""
            SELECT j FROM JuristEntity j
            JOIN FETCH j.profileStatusEntity
            WHERE j.id = :id
    """)
    Optional<JuristEntity> findWithProfileStatusById(@Param("id") UUID id);


    @Query("""
            SELECT j FROM JuristEntity j 
            JOIN FETCH j.typeEntity
            JOIN FETCH j.specializations 
            WHERE j.id = :id 
            AND j.isApproved = true
            AND j.profileStatusEntity.profileStatus = com.aidcompass.users.profile_status.models.ProfileStatus.COMPLETE
    """)
    Optional<JuristEntity> findWithTypeAndSpecsById(@Param("id") UUID id);


    @Query("""
                SELECT j FROM JuristEntity j 
                JOIN FETCH j.typeEntity
                JOIN FETCH j.specializations 
                JOIN FETCH j.profileStatusEntity 
                WHERE j.id = :id
    """)
    Optional<JuristEntity> findWithTypeAndSpecsAndProfileStatusById(@Param("id") UUID id);


    @Query("""
            SELECT j FROM JuristEntity j 
            JOIN FETCH j.typeEntity
            JOIN FETCH j.specializations 
            JOIN FETCH j.detailEntity
            WHERE j.id = :id 
            AND j.isApproved = true 
            AND j.profileStatusEntity.profileStatus = com.aidcompass.users.profile_status.models.ProfileStatus.COMPLETE
    """)
    Optional<JuristEntity> findWithTypeAndSpecsAndDetailById(@Param("id") UUID id);


    @Query("""
            SELECT j FROM JuristEntity j
            JOIN FETCH j.typeEntity
            JOIN FETCH j.specializations
            JOIN FETCH j.profileStatusEntity 
            JOIN FETCH j.detailEntity
            WHERE j.id = :id
    """)
    Optional<JuristEntity> findWithAllById(@Param("id") UUID id);


    @Query(
            value = """
                SELECT j FROM JuristEntity j
                JOIN FETCH j.typeEntity 
                WHERE j.isApproved = true 
                AND j.profileStatusEntity.profileStatus = com.aidcompass.users.profile_status.models.ProfileStatus.COMPLETE
            """,
            countQuery = """
                SELECT COUNT(j) FROM JuristEntity j
                WHERE j.isApproved = true
                AND j.profileStatusEntity.profileStatus = com.aidcompass.users.profile_status.models.ProfileStatus.COMPLETE
            """
    )
    Page<JuristEntity> findAllByApprovedTrue(Pageable pageable);


    @Query(
            value = """
                SELECT j FROM JuristEntity j
                JOIN FETCH j.typeEntity
                JOIN FETCH j.profileStatusEntity 
                JOIN FETCH j.detailEntity
                WHERE j.isApproved = false 
            """,
            countQuery = """
                SELECT COUNT(j) FROM JuristEntity j
                WHERE j.isApproved = false
            """
    )
    Page<JuristEntity> findAllByApprovedFalse(Pageable pageable);

    @Query(
            value = """
                SELECT j FROM JuristEntity j
                JOIN FETCH j.typeEntity
                WHERE j.gender = :gender
                AND j.isApproved = true
                AND j.profileStatusEntity.profileStatus = com.aidcompass.users.profile_status.models.ProfileStatus.COMPLETE
            """,
            countQuery = """
                SELECT COUNT(j) FROM JuristEntity j
                WHERE j.gender = :gender
                AND j.isApproved = true
                AND j.profileStatusEntity.profileStatus = com.aidcompass.users.profile_status.models.ProfileStatus.COMPLETE
            """
    )
    Page<JuristEntity> findAllByGender(@Param("gender") Gender gender, Pageable pageable);

    @EntityGraph(attributePaths = {"profileStatusEntity", "typeEntity"})
    @NonNull
    Page<JuristEntity> findAll(Specification<JuristEntity> specification, @NonNull Pageable pageable);

    @Modifying
    @Query("""
            UPDATE JuristEntity j
            SET j.profileProgress = j.profileProgress + :profileProgressStep,
                j.profileStatusEntity = :profileStatusEntity
            WHERE j.id = :id
    """)
    void updateProfileProgressAndStatus(@Param("id") UUID id, @Param("profileProgressStep") int profileProgressStep,
                                        @Param("profileStatusEntity") ProfileStatusEntity profileStatusEntity);

    @Modifying
    @Query("""
            UPDATE JuristEntity j
            SET j.profileProgress = j.profileProgress + :profileProgressStep
            WHERE j.id = :id
    """)
    void updateProfileProgress(@Param("id") UUID id, @Param("profileProgressStep") int profileProgressStep);

    long countByIsApproved(boolean approved);

    @EntityGraph(attributePaths = {"typeEntity"})
    List<JuristEntity> findAllByIdIn(Set<UUID> ids);
}