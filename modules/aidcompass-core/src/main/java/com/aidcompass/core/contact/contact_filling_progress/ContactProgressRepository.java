package com.aidcompass.core.contact.contact_filling_progress;

import com.aidcompass.core.contact.contact_filling_progress.models.ContactProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContactProgressRepository extends JpaRepository<ContactProgressEntity, UUID> {

    @Query("""
            SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
            FROM ContactProgressEntity p
            WHERE p.userId = :ownerId
            AND p.filledEmail = true
            AND p.filledPhone = true
    """)
    boolean isComplete(UUID ownerId);
}
