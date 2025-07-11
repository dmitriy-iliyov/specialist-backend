package com.aidcompass.specialistdirectory.domain.review;

import com.aidcompass.specialistdirectory.domain.review.models.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {
}
