package com.aidcompass.users.profile_status;


import com.aidcompass.users.general.exceptions.ProfileStatusEntityNotFoundByStatusException;
import com.aidcompass.users.profile_status.models.ProfileStatus;
import com.aidcompass.users.profile_status.models.ProfileStatusEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileStatusService {

    private final ProfileStatusRepository repository;
    private final ProfileStatusMapper mapper;


    @Transactional
    public void saveAll(List<ProfileStatus> statusList) {
        List<ProfileStatusEntity> entityList = mapper.toEntityList(statusList);
        repository.saveAll(entityList);
    }

    @Transactional(readOnly = true)
    public ProfileStatusEntity findByStatus(ProfileStatus status) {
        return repository.findByProfileStatus(status).orElseThrow(ProfileStatusEntityNotFoundByStatusException::new);
    }
}
