package com.aidcompass.core.contact.core.repositories;

import com.aidcompass.contracts.ContactType;
import com.aidcompass.core.contact.contact_type.models.ContactTypeEntity;
import com.aidcompass.core.contact.core.models.entity.ContactEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<ContactEntity, Long> {

    @Query("SELECT count(c) FROM ContactEntity c WHERE c.ownerId = :ownerId AND c.typeEntity.type = :type")
    long countByOwnerIdAndContentType(@Param("ownerId") UUID ownerId, @Param("type") ContactType type);

    boolean existsByTypeEntityAndContact(ContactTypeEntity type, String contact);

    @EntityGraph(attributePaths = {"typeEntity"})
    List<ContactEntity> findByOwnerId(UUID ownerId);

    @EntityGraph(attributePaths = {"typeEntity"})
    List<ContactEntity> findByOwnerIdAndIsConfirmed(UUID ownerId, boolean isConfirmed);

    @EntityGraph(attributePaths = {"typeEntity"})
    Optional<ContactEntity> findByContact(String contact);

    @EntityGraph(attributePaths = {"typeEntity"})
    List<ContactEntity> findByOwnerIdAndIsPrimary(UUID ownerId, boolean isPrimary);

    void deleteAllByOwnerId(UUID ownerId);

    @Query(
            value = "SELECT EXISTS(SELECT 1 FROM contacts WHERE id = :id AND is_confirmed = true)",
            nativeQuery = true
    )
    boolean isContactConfirmed(@Param("id") Long id);

    @Query("SELECT c FROM ContactEntity c WHERE c.ownerId = :ownerId AND c.isLinkedToAccount = : isLinkedToAccount")
    Optional<ContactEntity> findByOwnerIdAndLinkedToAccount(@Param("ownerId") UUID ownerId,
                                                            @Param("isLinkedToAccount") boolean isLinkedToAccount);

    @Modifying
    @Query(value = "UPDATE contacts SET is_confirmed = true WHERE id = :contactId", nativeQuery = true)
    int confirmContactById(@Param("contactId") Long contactId);

    @EntityGraph(attributePaths = {"typeEntity"})
    Optional<ContactEntity> findWithTypeById(Long contactId);

    @EntityGraph(attributePaths = {"typeEntity"})
    List<ContactEntity> findAllPrimaryByOwnerIdIn(Set<UUID> ids);

    @EntityGraph(attributePaths = {"typeEntity"})
    List<ContactEntity> findAllByOwnerIdIn(Set<UUID> ids);
}
