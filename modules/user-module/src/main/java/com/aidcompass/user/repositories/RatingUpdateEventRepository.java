package com.aidcompass.user.repositories;

import com.aidcompass.user.models.RatingUpdateEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RatingUpdateEventRepository extends JpaRepository<RatingUpdateEventEntity, UUID> {
}
