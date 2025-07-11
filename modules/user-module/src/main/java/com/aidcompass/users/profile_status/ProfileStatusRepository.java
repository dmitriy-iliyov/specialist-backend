package com.aidcompass.users.profile_status;

import com.aidcompass.users.profile_status.models.ProfileStatus;
import com.aidcompass.users.profile_status.models.ProfileStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileStatusRepository extends JpaRepository<ProfileStatusEntity, Long > {
    Optional<ProfileStatusEntity> findByProfileStatus(ProfileStatus status);
}
